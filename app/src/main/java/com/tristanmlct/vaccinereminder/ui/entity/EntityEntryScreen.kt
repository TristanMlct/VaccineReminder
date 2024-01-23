package com.tristanmlct.vaccinereminder.ui.entity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tristanmlct.vaccinereminder.R
import com.tristanmlct.vaccinereminder.VaccineReminderTopBar
import com.tristanmlct.vaccinereminder.ui.AppViewModelProvider
import com.tristanmlct.vaccinereminder.ui.navigation.NavigationDestination


object EntityEntryDestination : NavigationDestination {
    override val route = "entity_entry"
    override val titleRes = R.string.entity_entry_title
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: EntityEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
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
            onSaveClick = { },
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
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}
