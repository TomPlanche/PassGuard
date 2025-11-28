package com.tom_planche_nils_galloux.passguard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tom_planche_nils_galloux.passguard.data.repository.ProfileRepository
import com.tom_planche_nils_galloux.passguard.ui.screens.checker.PasswordCheckerScreen
import com.tom_planche_nils_galloux.passguard.ui.screens.generator.PasswordGeneratorScreen
import com.tom_planche_nils_galloux.passguard.ui.screens.profiles.EditRuleScreen
import com.tom_planche_nils_galloux.passguard.ui.screens.profiles.RuleProfilesScreen
import com.tom_planche_nils_galloux.passguard.ui.viewmodel.EditRuleViewModel
import com.tom_planche_nils_galloux.passguard.ui.viewmodel.RuleProfilesViewModel

@Composable
fun PassGuardNavHost(
    navController: NavHostController,
    repository: ProfileRepository,
    modifier: Modifier = Modifier
) {
    val profilesViewModel = remember { RuleProfilesViewModel(repository) }
    val editRuleViewModel = remember { EditRuleViewModel(repository) }
    NavHost(
        navController = navController,
        startDestination = Screen.Generator.route,
        modifier = modifier
    ) {
        composable(Screen.Generator.route) {
            PasswordGeneratorScreen(
                onNavigateToProfiles = {
                    navController.navigate(Screen.Profiles.route)
                }
            )
        }

        composable(Screen.Checker.route) {
            PasswordCheckerScreen(
                onNavigateToProfiles = {
                    navController.navigate(Screen.Profiles.route)
                }
            )
        }

        composable(Screen.Profiles.route) {
            RuleProfilesScreen(
                viewModel = profilesViewModel,
                onNavigateToEditRule = { ruleId ->
                    navController.navigate(Screen.EditRule.createRoute(ruleId))
                }
            )
        }

        composable(
            route = Screen.EditRule.route,
            arguments = listOf(
                navArgument("ruleId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val ruleId = backStackEntry.arguments?.getString("ruleId")
            EditRuleScreen(
                viewModel = editRuleViewModel,
                ruleId = if (ruleId == "new") null else ruleId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
