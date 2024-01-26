package com.tristanmlct.vaccinereminder.ui.entity

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.tristanmlct.vaccinereminder.R
import com.tristanmlct.vaccinereminder.VaccineReminderTopBar
import com.tristanmlct.vaccinereminder.ui.AppViewModelProvider
import com.tristanmlct.vaccinereminder.ui.navigation.NavigationDestination
import com.tristanmlct.vaccinereminder.utils.ReminderWorker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


object EntityEntryDestination : NavigationDestination {
    override val route = "entity_entry"
    override val titleRes = R.string.vaccine_entry_title
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityEntryScreen(
    context: Context,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: EntityEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            VaccineReminderTopBar(
                title = stringResource(EntityEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntityEntryBody(
            entityUiState = viewModel.entityUiState,
            onEntityValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveEntity()
                    navigateBack()

                    val message = "Reminder for your vaccine ${viewModel.entityUiState.entityDetails.vaccineName} for ${viewModel.entityUiState.entityDetails.name}"
                    val today = LocalDate.now()
                    val localDate = LocalDate.parse(viewModel.entityUiState.entityDetails.date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    val elapsedTime = ChronoUnit.DAYS.between(today, localDate)
                    createWorkRequest(message, elapsedTime, context)
                    Toast.makeText(context, "Reminder set", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntityEntryBody(
    entityUiState: EntityUiState,
    onEntityValueChange: (EntityDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        EntityInputForm(
            entityDetails = entityUiState.entityDetails,
            onValueChange = onEntityValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = entityUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityInputForm(
    entityDetails: EntityDetails,
    modifier: Modifier = Modifier,
    onValueChange: (EntityDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = entityDetails.name,
            onValueChange = { onValueChange(entityDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.entity_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = entityDetails.vaccineName,
            onValueChange = { onValueChange(entityDetails.copy(vaccineName = it)) },
            label = { Text(stringResource(R.string.vaccine_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)
        val calendar = Calendar.getInstance()

        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)

        var showDatePicker by remember {
            mutableStateOf(false)
        }

        var selectedDate by remember {
            mutableLongStateOf(calendar.timeInMillis)
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis!!
                        onValueChange(entityDetails.copy(date = formatter.format(Date(selectedDate))))
                    }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }

        Button(
            onClick = {
                showDatePicker = true
            }
        ) {
            Text(text = "Select a date")
        }

        Text(
            text = "Selected date: ${entityDetails.date}"
        )

        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

private fun createWorkRequest(message: String,timeDelayInSeconds: Long, context: Context) {
    val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
        .setInputData(workDataOf(
            "title" to "Reminder",
            "message" to message,
        )
        )
        .build()

    WorkManager.getInstance(context).enqueue(myWorkRequest)
}