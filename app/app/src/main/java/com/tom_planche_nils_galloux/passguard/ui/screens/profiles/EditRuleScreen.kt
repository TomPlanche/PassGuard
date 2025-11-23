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
fun EditRuleScreen(
    ruleId: String?,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isNewRule = ruleId == null

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isNewRule) "Create New Rule" else "Edit Rule",
            style = MaterialTheme.typography.headlineMedium
        )
        // TODO: Implement edit rule UI
        // - Profile name input
        // - Length configuration (min/max)
        // - Character type requirements
        //   - Uppercase required/optional/forbidden
        //   - Lowercase required/optional/forbidden
        //   - Numbers required/optional/forbidden
        //   - Symbols required/optional/forbidden
        // - Minimum count for each character type
        // - Forbidden characters input
        // - Pattern configuration (e.g., must start with letter)
        // - Charset restrictions
        // - Save button
        // - Cancel button
    }
}

@Preview(showBackground = true)
@Composable
private fun EditRuleScreenPreviewNew() {
    PassGuardTheme {
        EditRuleScreen(
            ruleId = null,
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditRuleScreenPreviewEdit() {
    PassGuardTheme {
        EditRuleScreen(
            ruleId = "existing-rule-id",
            onNavigateBack = {}
        )
    }
}
