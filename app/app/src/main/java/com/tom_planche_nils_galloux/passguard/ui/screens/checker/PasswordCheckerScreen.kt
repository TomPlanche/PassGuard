package com.tom_planche_nils_galloux.passguard.ui.screens.checker

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
fun PasswordCheckerScreen(
    onNavigateToProfiles: () -> Unit,
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
            text = "Password Checker",
            style = MaterialTheme.typography.headlineMedium
        )
        // TODO: Implement password checker UI
        // - Password input field
        // - Real-time validation feedback
        // - Strength score (0-100)
        // - Strength category (Very Weak / Weak / Medium / Strong / Very Strong)
        // - Rule compliance checklist
        // - Improvement suggestions
        // - Rule profile selector
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordCheckerScreenPreview() {
    PassGuardTheme {
        PasswordCheckerScreen(
            onNavigateToProfiles = {}
        )
    }
}
