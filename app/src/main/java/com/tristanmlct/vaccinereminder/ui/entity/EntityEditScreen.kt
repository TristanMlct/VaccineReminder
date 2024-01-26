package com.tristanmlct.vaccinereminder.ui.entity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tristanmlct.vaccinereminder.R
import com.tristanmlct.vaccinereminder.VaccineReminderTopBar
import com.tristanmlct.vaccinereminder.ui.AppViewModelProvider
import com.tristanmlct.vaccinereminder.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch


object EntityEditDestination : NavigationDestination {
    override val route = "entity_edit"
    override val titleRes = R.string.edit_vaccine_title
    const val entityIdArg = "entityId"
    val routeWithArgs = "$route/{$entityIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntityEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            VaccineReminderTopBar(
                title = stringResource(EntityEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntityEntryBody(
            entityUiState = viewModel.entityUiState,
            onEntityValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateEntity()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

