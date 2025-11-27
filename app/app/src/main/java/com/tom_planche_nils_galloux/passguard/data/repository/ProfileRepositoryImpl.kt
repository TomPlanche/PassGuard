package com.tom_planche_nils_galloux.passguard.data.repository

import com.tom_planche_nils_galloux.passguard.data.local.ProfileDataStore
import com.tom_planche_nils_galloux.passguard.data.model.RuleProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.InternalSerializationApi

/**
 * Implementation of ProfileRepository using ProfileDataStore.
 *
 * This class provides the concrete implementation of profile data operations
 * by delegating to the ProfileDataStore.
 *
 * @property dataStore The data store instance to use for persistence
 */
class ProfileRepositoryImpl(
    private val dataStore: ProfileDataStore
) : ProfileRepository {

    @OptIn(InternalSerializationApi::class)
    override val profilesFlow: Flow<List<RuleProfile>>
        get() = dataStore.profilesFlow

    @OptIn(InternalSerializationApi::class)
    override suspend fun getProfiles(): List<RuleProfile> {
        return dataStore.getProfiles()
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun getProfileById(id: String): RuleProfile? {
        return dataStore.getProfileById(id)
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun saveProfile(profile: RuleProfile) {
        dataStore.saveProfile(profile)
    }

    override suspend fun deleteProfile(id: String): Boolean {
        return dataStore.deleteProfile(id)
    }

    override suspend fun deleteAllProfiles() {
        dataStore.deleteAllProfiles()
    }

    override suspend fun exportProfilesToJson(): String {
        return dataStore.exportProfilesToJson()
    }

    override suspend fun importProfilesFromJson(jsonString: String): Int {
        return dataStore.importProfilesFromJson(jsonString)
    }

    override suspend fun initializeDefaultProfiles() {
        dataStore.initializeDefaultProfiles()
    }
}
