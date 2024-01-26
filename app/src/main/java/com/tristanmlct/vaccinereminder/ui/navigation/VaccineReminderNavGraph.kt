package com.tristanmlct.vaccinereminder.ui.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tristanmlct.vaccinereminder.ui.entity.EntityDetailsDestination
import com.tristanmlct.vaccinereminder.ui.entity.EntityDetailsScreen
import com.tristanmlct.vaccinereminder.ui.entity.EntityEditDestination
import com.tristanmlct.vaccinereminder.ui.entity.EntityEditScreen
import com.tristanmlct.vaccinereminder.ui.entity.EntityEntryDestination
import com.tristanmlct.vaccinereminder.ui.entity.EntityEntryScreen
import com.tristanmlct.vaccinereminder.ui.home.HomeDestination
import com.tristanmlct.vaccinereminder.ui.home.HomeScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VaccineReminderNavHost(
    context: Context,
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
                    navController.navigate("${EntityDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = EntityEntryDestination.route) {
            EntityEntryScreen(
                context = context,
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
                navigateToEditEntity = { navController.navigate("${EntityEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = EntityEditDestination.routeWithArgs,
            arguments = listOf(navArgument(EntityEditDestination.entityIdArg) {
                type = NavType.IntType
            })
        ) {
            EntityEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}