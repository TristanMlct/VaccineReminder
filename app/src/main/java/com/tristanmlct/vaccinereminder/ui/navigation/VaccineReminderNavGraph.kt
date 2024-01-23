package com.tristanmlct.vaccinereminder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tristanmlct.vaccinereminder.ui.entity.EntityDetailsDestination
import com.tristanmlct.vaccinereminder.ui.entity.EntityDetailsScreen
import com.tristanmlct.vaccinereminder.ui.entity.EntityEntryDestination
import com.tristanmlct.vaccinereminder.ui.entity.EntityEntryScreen
import com.tristanmlct.vaccinereminder.ui.home.HomeDestination
import com.tristanmlct.vaccinereminder.ui.home.HomeScreen


/**
 * Provide navigation graph for the app
 */
@Composable
fun VaccineReminderNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToEntityEntry = { navController.navigate(EntityEntryDestination.route) },
                navigateToEntityUpdate = {
                    navController.navigate("${EntityEntryDestination.route}/${it}")
                }
            )
        }
        composable(route = EntityEntryDestination.route) {
            EntityEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = EntityDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(EntityDetailsDestination.entityIdArg) {
                type = NavType.IntType
            })
        ) {
            EntityDetailsScreen(
                // navigateToEditEntity = { navController.navigate("${EntityEditDestination.route}/$it") },
                navigateToEditEntity = {},
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}