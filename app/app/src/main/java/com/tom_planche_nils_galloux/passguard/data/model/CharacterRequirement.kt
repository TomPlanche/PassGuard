package com.tom_planche_nils_galloux.passguard.data.model

import kotlinx.serialization.Serializable

/**
 * Represents the requirement level for a character type in a password rule.
 */
@Serializable
enum class CharacterRequirement {
    /**
     * This character type must be present in the password.
     */
    REQUIRED,

    /**
     * This character type is optional (can be present or absent).
     */
    OPTIONAL,

    /**
     * This character type must not be present in the password.
     */
    FORBIDDEN;

    /**
     * Returns a human-readable label for this requirement level.
     */
    fun getLabel(): String = when (this) {
        REQUIRED -> "Requis"
        OPTIONAL -> "Optionnel"
        FORBIDDEN -> "Interdit"
    }
}
