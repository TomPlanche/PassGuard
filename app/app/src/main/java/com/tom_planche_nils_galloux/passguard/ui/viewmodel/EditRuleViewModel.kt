package com.tom_planche_nils_galloux.passguard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom_planche_nils_galloux.passguard.data.model.CharacterRequirement
import com.tom_planche_nils_galloux.passguard.data.model.CharacterType
import com.tom_planche_nils_galloux.passguard.data.model.RuleProfile
import com.tom_planche_nils_galloux.passguard.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

/**
 * ViewModel for creating or editing a rule profile.
 *
 * This ViewModel manages the form state for creating or editing a profile,
 * handles validation, and saves changes to the repository.
 *
 * @property repository The repository to use for profile operations
 */
class EditRuleViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _profileId = MutableStateFlow<String?>(null)
    val profileId: StateFlow<String?> = _profileId.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _minLength = MutableStateFlow(8)
    val minLength: StateFlow<Int> = _minLength.asStateFlow()

    private val _maxLength = MutableStateFlow(64)
    val maxLength: StateFlow<Int> = _maxLength.asStateFlow()

    private val _characterRules = MutableStateFlow(
        mapOf(
            CharacterType.UPPERCASE to CharacterRequirement.OPTIONAL,
            CharacterType.LOWERCASE to CharacterRequirement.OPTIONAL,
            CharacterType.DIGITS to CharacterRequirement.OPTIONAL,
            CharacterType.SYMBOLS to CharacterRequirement.OPTIONAL
        )
    )
    val characterRules: StateFlow<Map<CharacterType, CharacterRequirement>> =
        _characterRules.asStateFlow()

    private val _minimumCounts = MutableStateFlow(
        mapOf(
            CharacterType.UPPERCASE to 0,
            CharacterType.LOWERCASE to 0,
            CharacterType.DIGITS to 0,
            CharacterType.SYMBOLS to 0
        )
    )
    val minimumCounts: StateFlow<Map<CharacterType, Int>> = _minimumCounts.asStateFlow()

    private val _forbiddenCharacters = MutableStateFlow("")
    val forbiddenCharacters: StateFlow<String> = _forbiddenCharacters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()

    /**
     * Loads a profile for editing by its ID.
     *
     * @param id The ID of the profile to load, or null to create a new profile
     */
    @OptIn(InternalSerializationApi::class)
    fun loadProfile(id: String?) {
        if (id == null) {
            resetForm()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val profile = repository.getProfileById(id)
                if (profile != null) {
                    _profileId.value = profile.id
                    _name.value = profile.name
                    _description.value = profile.description
                    _minLength.value = profile.minLength
                    _maxLength.value = profile.maxLength
                    _characterRules.value = profile.characterRules
                    _minimumCounts.value = profile.minimumCounts
                    _forbiddenCharacters.value = profile.forbiddenCharacters.joinToString("")
                } else {
                    _error.value = "Profil non trouvé"
                }
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Updates the profile name.
     */
    fun updateName(newName: String) {
        _name.value = newName
    }

    /**
     * Updates the profile description.
     */
    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    /**
     * Updates the minimum length.
     */
    fun updateMinLength(newMinLength: Int) {
        _minLength.value = newMinLength.coerceIn(1, _maxLength.value)
    }

    /**
     * Updates the maximum length.
     */
    fun updateMaxLength(newMaxLength: Int) {
        _maxLength.value = newMaxLength.coerceAtLeast(_minLength.value)
    }

    /**
     * Updates the requirement for a specific character type.
     */
    fun updateCharacterRequirement(type: CharacterType, requirement: CharacterRequirement) {
        _characterRules.value = _characterRules.value.toMutableMap().apply {
            this[type] = requirement
        }
    }

    /**
     * Updates the minimum count for a specific character type.
     */
    fun updateMinimumCount(type: CharacterType, count: Int) {
        _minimumCounts.value = _minimumCounts.value.toMutableMap().apply {
            this[type] = count.coerceAtLeast(0)
        }
    }

    /**
     * Updates the forbidden characters string.
     */
    fun updateForbiddenCharacters(chars: String) {
        _forbiddenCharacters.value = chars
    }

    /**
     * Saves the current profile.
     */
    @OptIn(InternalSerializationApi::class)
    fun saveProfile() {
        if (_name.value.isBlank()) {
            _error.value = "Le nom du profil est requis"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val now = System.currentTimeMillis()
                val profile = RuleProfile(
                    id = _profileId.value ?: RuleProfile.generateId(),
                    name = _name.value,
                    description = _description.value,
                    minLength = _minLength.value,
                    maxLength = _maxLength.value,
                    characterRules = _characterRules.value,
                    minimumCounts = _minimumCounts.value,
                    forbiddenCharacters = _forbiddenCharacters.value.toSet(),
                    createdAt = now,
                    updatedAt = now
                )

                if (!profile.isValid()) {
                    _error.value = "Configuration de profil invalide"
                    return@launch
                }

                repository.saveProfile(profile)
                _isSaved.value = true
            } catch (e: Exception) {
                _error.value = "Erreur lors de la sauvegarde: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Resets the form to default values for creating a new profile.
     */
    private fun resetForm() {
        _profileId.value = null
        _name.value = ""
        _description.value = ""
        _minLength.value = 8
        _maxLength.value = 64
        _characterRules.value = mapOf(
            CharacterType.UPPERCASE to CharacterRequirement.OPTIONAL,
            CharacterType.LOWERCASE to CharacterRequirement.OPTIONAL,
            CharacterType.DIGITS to CharacterRequirement.OPTIONAL,
            CharacterType.SYMBOLS to CharacterRequirement.OPTIONAL
        )
        _minimumCounts.value = mapOf(
            CharacterType.UPPERCASE to 0,
            CharacterType.LOWERCASE to 0,
            CharacterType.DIGITS to 0,
            CharacterType.SYMBOLS to 0
        )
        _forbiddenCharacters.value = ""
        _isSaved.value = false
        _error.value = null
    }

    /**
     * Clears the current error message.
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Resets the saved state.
     */
    fun resetSavedState() {
        _isSaved.value = false
    }
}
