package com.tom_planche_nils_galloux.passguard.data.model

import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a password rule profile with customizable requirements.
 *
 * @property id Unique identifier for the profile
 * @property name Name of the profile
 * @property description Optional description of the profile
 * @property minLength Minimum password length (default: 8)
 * @property maxLength Maximum password length (default: 64)
 * @property characterRules Map of character types to their requirement level
 * @property minimumCounts Map of character types to their minimum required count
 * @property forbiddenCharacters Set of characters that must not be present
 * @property createdAt Timestamp when the profile was created
 * @property updatedAt Timestamp when the profile was last updated
 */
@Serializable
@kotlinx.serialization.InternalSerializationApi
data class RuleProfile(
    val id: String,
    val name: String,
    val description: String = "",
    val minLength: Int = 8,
    val maxLength: Int = 64,
    val characterRules: Map<CharacterType, CharacterRequirement> = mapOf(
        CharacterType.UPPERCASE to CharacterRequirement.OPTIONAL,
        CharacterType.LOWERCASE to CharacterRequirement.OPTIONAL,
        CharacterType.DIGITS to CharacterRequirement.OPTIONAL,
        CharacterType.SYMBOLS to CharacterRequirement.OPTIONAL
    ),
    val minimumCounts: Map<CharacterType, Int> = mapOf(
        CharacterType.UPPERCASE to 0,
        CharacterType.LOWERCASE to 0,
        CharacterType.DIGITS to 0,
        CharacterType.SYMBOLS to 0
    ),
    val forbiddenCharacters: Set<Char> = emptySet(),
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        /**
         * Generates a unique ID for a profile.
         */
        fun generateId(): String = UUID.randomUUID().toString()

        /**
         * Creates a default profile with balanced requirements.
         */
        fun createDefault(): RuleProfile {
            val now = System.currentTimeMillis()
            return RuleProfile(
                id = generateId(),
                name = "Général",
                description = "Profil par défaut avec des règles équilibrées",
                minLength = 12,
                maxLength = 32,
                characterRules = mapOf(
                    CharacterType.UPPERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.LOWERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.DIGITS to CharacterRequirement.REQUIRED,
                    CharacterType.SYMBOLS to CharacterRequirement.OPTIONAL
                ),
                minimumCounts = mapOf(
                    CharacterType.UPPERCASE to 1,
                    CharacterType.LOWERCASE to 1,
                    CharacterType.DIGITS to 1,
                    CharacterType.SYMBOLS to 0
                ),
                createdAt = now,
                updatedAt = now
            )
        }

        /**
         * Creates a profile suitable for Gmail.
         */
        fun createGmailProfile(): RuleProfile {
            val now = System.currentTimeMillis()
            return RuleProfile(
                id = generateId(),
                name = "Gmail",
                description = "Profil pour les comptes Gmail",
                minLength = 12,
                maxLength = 100,
                characterRules = mapOf(
                    CharacterType.UPPERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.LOWERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.DIGITS to CharacterRequirement.REQUIRED,
                    CharacterType.SYMBOLS to CharacterRequirement.OPTIONAL
                ),
                minimumCounts = mapOf(
                    CharacterType.UPPERCASE to 1,
                    CharacterType.LOWERCASE to 1,
                    CharacterType.DIGITS to 1,
                    CharacterType.SYMBOLS to 0
                ),
                createdAt = now,
                updatedAt = now
            )
        }

        /**
         * Creates a profile suitable for banking applications.
         */
        fun createBankingProfile(): RuleProfile {
            val now = System.currentTimeMillis()
            return RuleProfile(
                id = generateId(),
                name = "Bancaire",
                description = "Profil pour les applications bancaires",
                minLength = 8,
                maxLength = 16,
                characterRules = mapOf(
                    CharacterType.UPPERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.LOWERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.DIGITS to CharacterRequirement.REQUIRED,
                    CharacterType.SYMBOLS to CharacterRequirement.REQUIRED
                ),
                minimumCounts = mapOf(
                    CharacterType.UPPERCASE to 1,
                    CharacterType.LOWERCASE to 1,
                    CharacterType.DIGITS to 1,
                    CharacterType.SYMBOLS to 1
                ),
                createdAt = now,
                updatedAt = now
            )
        }

        /**
         * Creates a profile suitable for gaming platforms.
         */
        fun createGamingProfile(): RuleProfile {
            val now = System.currentTimeMillis()
            return RuleProfile(
                id = generateId(),
                name = "Gaming",
                description = "Profil pour les plateformes de jeux",
                minLength = 8,
                maxLength = 32,
                characterRules = mapOf(
                    CharacterType.UPPERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.LOWERCASE to CharacterRequirement.REQUIRED,
                    CharacterType.DIGITS to CharacterRequirement.REQUIRED,
                    CharacterType.SYMBOLS to CharacterRequirement.OPTIONAL
                ),
                minimumCounts = mapOf(
                    CharacterType.UPPERCASE to 1,
                    CharacterType.LOWERCASE to 1,
                    CharacterType.DIGITS to 1,
                    CharacterType.SYMBOLS to 0
                ),
                createdAt = now,
                updatedAt = now
            )
        }
    }

    /**
     * Validates if this profile has valid configuration.
     */
    fun isValid(): Boolean {
        return minLength > 0 &&
                maxLength >= minLength &&
                name.isNotBlank() &&
                characterRules.values.any { it != CharacterRequirement.FORBIDDEN }
    }

    /**
     * Returns a copy of this profile with updated timestamp.
     */
    fun withUpdatedTimestamp(): RuleProfile = copy(updatedAt = System.currentTimeMillis())
}
