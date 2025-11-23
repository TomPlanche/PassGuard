package com.tom_planche_nils_galloux.passguard.ui.screens.profiles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tom_planche_nils_galloux.passguard.ui.theme.PassGuardTheme

@Composable
fun RuleProfilesScreen(
    onNavigateToEditRule: (ruleId: String?) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Rule Profiles",
            style = MaterialTheme.typography.headlineMedium
        )
        // TODO: Implement rule profiles UI
        // - List of saved rule profiles
        // - Pre-registered profiles (Gmail, Banking, Gaming, etc.)
        // - Create new profile button
        // - Edit profile button
        // - Delete profile button
        // - Export profile to JSON
        // - Import profile from JSON
    }
}

@Preview(showBackground = true)
@Composable
private fun RuleProfilesScreenPreview() {
    PassGuardTheme {
        RuleProfilesScreen(
            onNavigateToEditRule = {},
            onNavigateBack = {}
        )
    }
}
