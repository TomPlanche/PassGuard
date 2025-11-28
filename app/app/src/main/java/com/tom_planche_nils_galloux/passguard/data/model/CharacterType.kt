package com.tom_planche_nils_galloux.passguard.data.model

import kotlinx.serialization.Serializable

/**
 * Represents the different types of characters that can be used in a password.
 */
@Serializable
enum class CharacterType {
    UPPERCASE,
    LOWERCASE,
    DIGITS,
    SYMBOLS;

    /**
     * Returns the character set for this character type.
     */
    fun getCharacterSet(): String = when (this) {
        UPPERCASE -> "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        LOWERCASE -> "abcdefghijklmnopqrstuvwxyz"
        DIGITS -> "0123456789"
        SYMBOLS -> "!@#$%^&*()_+-=[]{}|;:,.<>?"
    }

    /**
     * Returns a human-readable label for this character type.
     */
    fun getLabel(): String = when (this) {
        UPPERCASE -> "Majuscules"
        LOWERCASE -> "Minuscules"
        DIGITS -> "Chiffres"
        SYMBOLS -> "Symboles"
    }
}
