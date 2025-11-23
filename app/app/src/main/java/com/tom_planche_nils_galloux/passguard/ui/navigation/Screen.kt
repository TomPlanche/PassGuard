package com.tom_planche_nils_galloux.passguard.ui.navigation

/**
 * Sealed class representing the different screens in the app.
 */
sealed class Screen(val route: String) {
    data object Generator : Screen("generator")
    data object Checker : Screen("checker")
    data object Profiles : Screen("profiles")
    data object EditRule : Screen("edit_rule/{ruleId}") {
        fun createRoute(ruleId: String? = null): String {
            return if (ruleId != null) "edit_rule/$ruleId" else "edit_rule/new"
        }
    }
}
