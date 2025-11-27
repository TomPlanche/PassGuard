package com.tom_planche_nils_galloux.passguard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom_planche_nils_galloux.passguard.data.model.RuleProfile
import com.tom_planche_nils_galloux.passguard.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

/**
 * ViewModel for managing the list of rule profiles.
 *
 * This ViewModel handles the business logic for displaying, creating, and deleting profiles.
 * It exposes the list of profiles as a StateFlow for the UI to observe.
 *
 * @property repository The repository to use for profile operations
 */
@OptIn(InternalSerializationApi::class)
class RuleProfilesViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _profiles = MutableStateFlow<List<RuleProfile>>(emptyList())
    val profiles: StateFlow<List<RuleProfile>> = _profiles.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadProfiles()
    }

    /**
     * Loads all profiles from the repository.
     */
    fun loadProfiles() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.profilesFlow.collect { profileList ->
                    _profiles.value = profileList
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des profils: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    /**
     * Deletes a profile by its ID.
     *
     * @param profileId The ID of the profile to delete
     */
    fun deleteProfile(profileId: String) {
        viewModelScope.launch {
            try {
                val success = repository.deleteProfile(profileId)
                if (!success) {
                    _error.value = "Profil non trouvé"
                }
            } catch (e: Exception) {
                _error.value = "Erreur lors de la suppression: ${e.message}"
            }
        }
    }

    /**
     * Clears the current error message.
     */
    fun clearError() {
        _error.value = null
    }
}
