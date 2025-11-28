package com.tom_planche_nils_galloux.passguard.ui.screens.profiles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tom_planche_nils_galloux.passguard.data.model.CharacterRequirement
import com.tom_planche_nils_galloux.passguard.data.model.CharacterType
import com.tom_planche_nils_galloux.passguard.ui.theme.PassGuardTheme
import com.tom_planche_nils_galloux.passguard.ui.viewmodel.EditRuleViewModel
import kotlin.math.roundToInt

@Composable
fun EditRuleScreen(
    viewModel: EditRuleViewModel,
    ruleId: String?,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val minLength by viewModel.minLength.collectAsState()
    val maxLength by viewModel.maxLength.collectAsState()
    val characterRules by viewModel.characterRules.collectAsState()
    val minimumCounts by viewModel.minimumCounts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val isSaved by viewModel.isSaved.collectAsState()

    val isNewRule = ruleId == null

    LaunchedEffect(ruleId) {
        viewModel.loadProfile(ruleId)
    }

    LaunchedEffect(isSaved) {
        if (isSaved) {
            onNavigateBack()
            viewModel.resetSavedState()
        }
    }

    if (isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = if (isNewRule) "Nouveau Profil" else "Modifier le Profil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Nom du profil") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = error != null && name.isBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("Description (optionnel)") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Longueur du mot de passe",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Minimum: $minLength caractères",
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = minLength.toFloat(),
                    onValueChange = { viewModel.updateMinLength(it.roundToInt()) },
                    valueRange = 4f..maxLength.toFloat(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Maximum: $maxLength caractères",
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = maxLength.toFloat(),
                    onValueChange = { viewModel.updateMaxLength(it.roundToInt()) },
                    valueRange = minLength.toFloat()..128f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Types de caractères",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                CharacterType.entries.forEach { type ->
                    CharacterTypeSection(
                        type = type,
                        requirement = characterRules[type] ?: CharacterRequirement.OPTIONAL,
                        minimumCount = minimumCounts[type] ?: 0,
                        onRequirementChange = { viewModel.updateCharacterRequirement(type, it) },
                        onMinimumCountChange = { viewModel.updateMinimumCount(type, it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (error != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Annuler")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { viewModel.saveProfile() },
                modifier = Modifier.weight(1f),
                enabled = name.isNotBlank()
            ) {
                Text("Sauvegarder")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CharacterTypeSection(
    type: CharacterType,
    requirement: CharacterRequirement,
    minimumCount: Int,
    onRequirementChange: (CharacterRequirement) -> Unit,
    onMinimumCountChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = type.getLabel(),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CharacterRequirement.entries.forEach { req ->
                FilterChip(
                    selected = requirement == req,
                    onClick = { onRequirementChange(req) },
                    label = { Text(req.getLabel()) }
                )
            }
        }

        if (requirement == CharacterRequirement.REQUIRED) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Minimum: $minimumCount",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Slider(
                value = minimumCount.toFloat(),
                onValueChange = { onMinimumCountChange(it.roundToInt()) },
                valueRange = 0f..10f,
                steps = 9,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditRuleScreenPreviewNew() {
    PassGuardTheme {
        // Preview without ViewModel
        Text("Preview requires ViewModel initialization")
    }
}
