package com.tom_planche_nils_galloux.passguard.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tom_planche_nils_galloux.passguard.data.model.RuleProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * DataStore extension for the Context.
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profiles")

/**
 * Manages the persistence of password rule profiles using DataStore.
 *
 * This class handles storing and retrieving profiles as JSON strings in DataStore.
 */
class ProfileDataStore(private val context: Context) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    companion object {
        private val PROFILES_KEY = stringPreferencesKey("profiles_json")
    }

    /**
     * Flow that emits the list of all profiles whenever it changes.
     */
    @OptIn(InternalSerializationApi::class)
    val profilesFlow: Flow<List<RuleProfile>> = context.dataStore.data
        .map { preferences ->
            val profilesJson = preferences[PROFILES_KEY] ?: "[]"
            try {
                json.decodeFromString<List<RuleProfile>>(profilesJson)
            } catch (e: Exception) {
                // If deserialization fails, return empty list
                emptyList()
            }
        }

    /**
     * Gets all profiles as a list.
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun getProfiles(): List<RuleProfile> {
        val preferences = context.dataStore.data.map { it }.first()
        val profilesJson = preferences[PROFILES_KEY] ?: "[]"
        return try {
            json.decodeFromString<List<RuleProfile>>(profilesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Gets a profile by its ID.
     *
     * @param id The unique identifier of the profile
     * @return The profile if found, null otherwise
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun getProfileById(id: String): RuleProfile? {
        return getProfiles().find { it.id == id }
    }

    /**
     * Saves a new profile or updates an existing one.
     *
     * @param profile The profile to save
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun saveProfile(profile: RuleProfile) {
        context.dataStore.edit { preferences ->
            val currentProfiles = getProfiles().toMutableList()
            val existingIndex = currentProfiles.indexOfFirst { it.id == profile.id }

            if (existingIndex >= 0) {
                // Update existing profile
                currentProfiles[existingIndex] = profile.withUpdatedTimestamp()
            } else {
                // Add new profile
                currentProfiles.add(profile)
            }

            val profilesJson = json.encodeToString(currentProfiles)
            preferences[PROFILES_KEY] = profilesJson
        }
    }

    /**
     * Deletes a profile by its ID.
     *
     * @param id The unique identifier of the profile to delete
     * @return true if the profile was deleted, false if it wasn't found
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun deleteProfile(id: String): Boolean {
        var deleted = false
        context.dataStore.edit { preferences ->
            val currentProfiles = getProfiles().toMutableList()
            val existingIndex = currentProfiles.indexOfFirst { it.id == id }

            if (existingIndex >= 0) {
                currentProfiles.removeAt(existingIndex)
                val profilesJson = json.encodeToString(currentProfiles)
                preferences[PROFILES_KEY] = profilesJson
                deleted = true
            }
        }
        return deleted
    }

    /**
     * Deletes all profiles.
     */
    suspend fun deleteAllProfiles() {
        context.dataStore.edit { preferences ->
            preferences[PROFILES_KEY] = "[]"
        }
    }

    /**
     * Exports all profiles as a JSON string.
     *
     * @return JSON string containing all profiles
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun exportProfilesToJson(): String {
        val profiles = getProfiles()
        return json.encodeToString(profiles)
    }

    /**
     * Imports profiles from a JSON string.
     *
     * @param jsonString JSON string containing the profiles
     * @return The number of profiles successfully imported
     * @throws kotlinx.serialization.SerializationException if the JSON is invalid
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun importProfilesFromJson(jsonString: String): Int {
        val importedProfiles = json.decodeFromString<List<RuleProfile>>(jsonString)
        val validProfiles = importedProfiles.filter { it.isValid() }

        context.dataStore.edit { preferences ->
            val currentProfiles = getProfiles().toMutableList()

            validProfiles.forEach { importedProfile ->
                val existingIndex = currentProfiles.indexOfFirst { it.id == importedProfile.id }
                if (existingIndex >= 0) {
                    // Update existing profile
                    currentProfiles[existingIndex] = importedProfile.withUpdatedTimestamp()
                } else {
                    // Add new profile
                    currentProfiles.add(importedProfile)
                }
            }

            val profilesJson = json.encodeToString(currentProfiles)
            preferences[PROFILES_KEY] = profilesJson
        }

        return validProfiles.size
    }

    /**
     * Initializes the DataStore with default profiles if it's empty.
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun initializeDefaultProfiles() {
        val currentProfiles = getProfiles()
        if (currentProfiles.isEmpty()) {
            val defaultProfiles = listOf(
                RuleProfile.createDefault(),
                RuleProfile.createGmailProfile(),
                RuleProfile.createBankingProfile(),
                RuleProfile.createGamingProfile()
            )

            context.dataStore.edit { preferences ->
                val profilesJson = json.encodeToString(defaultProfiles)
                preferences[PROFILES_KEY] = profilesJson
            }
        }
    }
}
