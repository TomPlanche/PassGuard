# PassGuard

A secure password generator Android application with customizable rules.

## Description

PassGuard is an Android mobile application that allows you to create strong and customized passwords according to your needs. The application provides an intuitive interface to define generation rules and ensure the security of your credentials.

## Features

The following features describe the complete vision for PassGuard. See [Development Status](#development-status) for current implementation progress.

### Password Generator

Generate random passwords based on configurable rules.

#### Standard Rules

- **Length**: Slider from 8 to 64 characters
- **Character types** (toggleable):
  - Uppercase (A-Z)
  - Lowercase (a-z)
  - Numbers (0-9)
  - Symbols (!@#$%^&*()_+-=[]{}|;:,.<>?)
- **Options**:
  - Exclude ambiguous characters (0/O, 1/l/I)
  - Avoid similar characters

#### Custom Rules

Key feature: Allow users to define rules specific to a company or service.

- Forbid specific characters (e.g., no & or <>)
- Require a minimum number of symbols (e.g., at least 3)
- Force presence of at least one character from each category
- Define a specific pattern (e.g., must start with a letter)
- Limit to a specific charset (e.g., alphanumeric only for legacy systems)

#### Rule Creation Interface

- List of pre-registered rules (Gmail, Banking, Gaming, etc.)
- Ability to create new custom rules
- Save configurations for reuse
- Rule naming (e.g., "EDF Rules", "Bank Rules")

#### Generator Features

- **Instant generation**: Password regenerates automatically when a rule changes
- **Quick copy**: Button to copy to clipboard
- **Regenerate**: Button to generate a new password with the same rules
- **Strength indicator**: Visual bar showing password complexity
- **Estimated crack time**: "This password would take 34 billion years to crack"

### Password Checker

Test the compliance of an existing password against defined rules.

#### Main Features

- **Real-time validation**:
  - User types their password
  - Validation occurs character by character
  - Immediate visual feedback for each rule
- **Strength calculation**:
  - Score from 0 to 100
  - Categorization: Very Weak / Weak / Medium / Strong / Very Strong
  - Factors considered:
    - Length
    - Character diversity
    - Entropy
    - Common patterns (e.g., "123", "abc", "qwerty")
    - Dictionary words
- **Detailed analysis**:
  - List of rules to follow with individual status
  - Contextual improvement suggestions
  - Visual progress indicator toward compliance
- **Verifiable rules**:
  - Minimum/maximum length
  - Presence of each character type (uppercase/lowercase/numbers/symbols)
  - Minimum number of characters of each type
  - Absence of forbidden characters
  - Absence of common sequences
  - Compliance with a custom rule profile

### User Interface

- **Rule profile management**
- **Custom profile creation**:
  - Profile name (e.g., "EDF Internal", "Client Portal")
  - Configuration of all rules
  - Persistent local storage
  - Edit and delete options
- **Profile sharing**:
  - Export to JSON
  - Import from file
  - Useful for standardizing rules within a team

## Technologies

- **Language**: Kotlin
- **IDE**: Android Studio
- **Platform**: Android (API 28+)
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Jetpack Navigation Compose
- **Data Persistence**: DataStore Preferences
- **Serialization**: Kotlinx Serialization JSON

## Architecture

PassGuard follows the **MVVM (Model-View-ViewModel)** architecture pattern with a clean separation of concerns:

### Data Layer
- **Models** (`data/model/`): Serializable data classes for password rule profiles
  - `RuleProfile`: Main profile data class with factory methods for pre-configured profiles
  - `CharacterType`: Enum for character types (uppercase, lowercase, digits, symbols)
  - `CharacterRequirement`: Enum for requirement levels (required, optional, forbidden)
- **Local Data Source** (`data/local/`): DataStore-based persistence
  - `ProfileDataStore`: Handles JSON serialization and CRUD operations
- **Repository** (`data/repository/`): Abstraction layer for data operations
  - `ProfileRepository`: Interface defining the data contract
  - `ProfileRepositoryImpl`: Concrete implementation

### UI Layer
- **Screens** (`ui/screens/`): Jetpack Compose UI components
  - Generator, Checker, Profiles, and EditRule screens
- **ViewModels** (`ui/viewmodel/`): State management and business logic (in progress)
- **Navigation** (`ui/navigation/`): Navigation graph with bottom navigation bar

### Benefits
- **Testability**: Clean separation allows easy unit testing of business logic
- **Maintainability**: Clear structure makes code easier to understand and modify
- **Scalability**: Repository pattern allows easy addition of remote data sources

## Installation

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run the application on an emulator or physical device

## Authors

- Tom Planche
- Nils Galloux

## License

This project is dual-licensed:

- [MIT](LICENCE-MIT)
- [Apache 2.0](LICENCE-APACHE)

You can choose the license that best suits your needs.
