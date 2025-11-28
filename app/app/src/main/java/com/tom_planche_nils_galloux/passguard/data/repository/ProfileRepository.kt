package com.tom_planche_nils_galloux.passguard.data.repository

import com.tom_planche_nils_galloux.passguard.data.model.RuleProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.InternalSerializationApi

/**
 * Repository interface for managing password rule profiles.
 *
 * This interface defines the contract for profile data operations,
 * providing an abstraction layer between the data source and the domain layer.
 */
interface ProfileRepository {
    /**
     * Flow that emits the list of all profiles whenever it changes.
     */
    @OptIn(InternalSerializationApi::class)
    val profilesFlow: Flow<List<RuleProfile>>

    /**
     * Gets all profiles as a list.
     *
     * @return List of all profiles
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun getProfiles(): List<RuleProfile>

    /**
     * Gets a profile by its ID.
     *
     * @param id The unique identifier of the profile
     * @return The profile if found, null otherwise
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun getProfileById(id: String): RuleProfile?

    /**
     * Saves a new profile or updates an existing one.
     *
     * @param profile The profile to save
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun saveProfile(profile: RuleProfile)

    /**
     * Deletes a profile by its ID.
     *
     * @param id The unique identifier of the profile to delete
     * @return true if the profile was deleted, false if it wasn't found
     */
    suspend fun deleteProfile(id: String): Boolean

    /**
     * Deletes all profiles.
     */
    suspend fun deleteAllProfiles()

    /**
     * Exports all profiles as a JSON string.
     *
     * @return JSON string containing all profiles
     */
    suspend fun exportProfilesToJson(): String

    /**
     * Imports profiles from a JSON string.
     *
     * @param jsonString JSON string containing the profiles
     * @return The number of profiles successfully imported
     * @throws kotlinx.serialization.SerializationException if the JSON is invalid
     */
    suspend fun importProfilesFromJson(jsonString: String): Int

    /**
     * Initializes the repository with default profiles if empty.
     */
    suspend fun initializeDefaultProfiles()
}
