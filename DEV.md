# Developer Documentation

This document provides an overview of the project structure and explains the role of each file and directory.

## Project Structure

```
PassGuard/
├── app/                          # Android Studio project root
│   ├── app/                      # Main application module
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/         # Kotlin source code
│   │   │   │   ├── res/          # Android resources
│   │   │   │   └── AndroidManifest.xml
│   │   │   ├── test/             # Unit tests
│   │   │   └── androidTest/      # Instrumented tests
│   │   └── build.gradle.kts      # App module build configuration
│   ├── gradle/
│   │   └── libs.versions.toml    # Dependency version catalog
│   ├── build.gradle.kts          # Project-level build configuration
│   └── settings.gradle.kts       # Project settings
├── README.md                     # Project overview
├── CLAUDE.md                     # Claude Code instructions
├── DEV.md                        # This file
├── LICENCE-MIT                   # MIT License
└── LICENCE-APACHE                # Apache 2.0 License
```

## Source Code Structure

### Package: `com.tom_planche_nils_galloux.passguard`

```
passguard/
├── MainActivity.kt               # App entry point
├── data/                         # Data layer
│   ├── model/                    # Data models
│   │   ├── RuleProfile.kt        # Password rule profile model
│   │   ├── CharacterType.kt      # Character type enum
│   │   └── CharacterRequirement.kt # Requirement level enum
│   ├── local/                    # Local data sources
│   │   └── ProfileDataStore.kt   # DataStore implementation for profiles
│   └── repository/               # Repository pattern
│       ├── ProfileRepository.kt  # Repository interface
│       └── ProfileRepositoryImpl.kt # Repository implementation
└── ui/
    ├── navigation/               # Navigation components
    │   ├── Screen.kt             # Route definitions
    │   └── PassGuardNavHost.kt   # Navigation graph
    ├── screens/                  # UI screens
    │   ├── generator/
    │   │   └── PasswordGeneratorScreen.kt
    │   ├── checker/
    │   │   └── PasswordCheckerScreen.kt
    │   └── profiles/
    │       ├── RuleProfilesScreen.kt
    │       └── EditRuleScreen.kt
    └── theme/                    # Material 3 theming
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## File Descriptions

### Root Files

| File | Description |
|------|-------------|
| `README.md` | Project overview, features, and installation instructions |
| `CLAUDE.md` | Instructions for Claude Code AI assistant |
| `DEV.md` | Developer documentation (this file) |
| `LICENCE-MIT` | MIT License text |
| `LICENCE-APACHE` | Apache 2.0 License text |

### Gradle Configuration

| File | Description |
|------|-------------|
| `app/build.gradle.kts` | Project-level Gradle configuration |
| `app/app/build.gradle.kts` | App module configuration (dependencies, SDK versions, build features) |
| `app/settings.gradle.kts` | Project settings and module declarations |
| `app/gradle.properties` | Gradle properties (JVM args, AndroidX flags) |
| `app/gradle/libs.versions.toml` | Centralized dependency version catalog |

### Source Files

#### Data Layer

| File | Description |
|------|-------------|
| `RuleProfile.kt` | Data class for password rule profiles with factory methods for pre-configured profiles (Default, Gmail, Banking, Gaming) |
| `CharacterType.kt` | Enum defining character types (UPPERCASE, LOWERCASE, DIGITS, SYMBOLS) with character sets |
| `CharacterRequirement.kt` | Enum defining requirement levels (REQUIRED, OPTIONAL, FORBIDDEN) for character types |
| `ProfileDataStore.kt` | DataStore-based persistence layer for profiles with JSON serialization, CRUD operations, and import/export functionality |
| `ProfileRepository.kt` | Repository interface defining the contract for profile data operations |
| `ProfileRepositoryImpl.kt` | Concrete implementation of ProfileRepository using ProfileDataStore |

#### UI Layer

| File | Description |
|------|-------------|
| `MainActivity.kt` | Main activity with Jetpack Compose setup, bottom navigation bar, and app scaffold |
| `Screen.kt` | Sealed class defining navigation routes for all screens |
| `PassGuardNavHost.kt` | Navigation host composable managing screen transitions |
| `PasswordGeneratorScreen.kt` | Screen for generating passwords with configurable rules (skeleton implementation) |
| `PasswordCheckerScreen.kt` | Screen for validating passwords against rules (skeleton implementation) |
| `RuleProfilesScreen.kt` | Screen listing all saved rule profiles (skeleton implementation) |
| `EditRuleScreen.kt` | Screen for creating or editing rule profiles (skeleton implementation) |

### Theme Files

| File | Description |
|------|-------------|
| `Color.kt` | Color palette definitions for light and dark themes |
| `Theme.kt` | Material 3 theme composable with dynamic color support |
| `Type.kt` | Typography configuration |

### Resources

| Directory | Description |
|-----------|-------------|
| `res/drawable/` | Vector graphics and launcher icon components |
| `res/mipmap-*/` | Launcher icons at various densities |
| `res/values/` | Strings, colors, and theme attributes |
| `res/xml/` | Backup and data extraction rules |

### Tests

| File | Description |
|------|-------------|
| `src/test/.../ExampleUnitTest.kt` | Local unit tests (run on JVM) |
| `src/androidTest/.../ExampleInstrumentedTest.kt` | Instrumented tests (run on device/emulator) |

## Architecture

The app follows the **MVVM** (Model-View-ViewModel) architecture pattern:

- **View**: Composable screens in `ui/screens/` (skeleton implementations)
- **ViewModel**: (To be implemented in Iteration 2) Business logic and state management
- **Model**: Data classes in `data/model/` with Repository pattern for data operations

### Data Layer (Implemented)

The data layer consists of:

- **Models** (`data/model/`): Serializable data classes representing password rule profiles
- **Local Data Source** (`data/local/`): DataStore-based persistence with JSON serialization
- **Repository** (`data/repository/`): Abstraction layer providing clean API for data operations

The repository pattern allows for easy testing and potential future addition of remote data sources.

## Navigation

The app uses Jetpack Compose Navigation with a bottom navigation bar containing three main destinations:

1. **Generator** - Password generation screen (start destination)
2. **Checker** - Password validation screen
3. **Profiles** - Rule profiles management

The `EditRuleScreen` is a detail screen accessible from `Profiles` and hides the bottom navigation bar.

## Building the Project

```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Check for lint issues
./gradlew lint
```

## Dependencies

Key dependencies (managed in `libs.versions.toml`):

### UI & Navigation
- **Jetpack Compose** (BOM 2024.09.00) - Modern declarative UI toolkit
- **Material 3** - Material Design 3 components
- **Navigation Compose** (2.7.7) - Type-safe navigation for Compose
- **Activity Compose** (1.8.0) - Compose integration for activities

### Data & State Management
- **DataStore Preferences** (1.0.0) - Modern data storage solution with async API
- **Kotlinx Serialization JSON** (1.6.0) - Kotlin serialization library for JSON
- **Lifecycle ViewModel Compose** (2.6.1) - ViewModel integration for Compose
- **Lifecycle Runtime KTX** (2.6.1) - Lifecycle-aware components

### Core Libraries
- **AndroidX Core KTX** (1.10.1) - Kotlin extensions for Android APIs

### Testing
- **JUnit** (4.13.2) - Unit testing framework
- **AndroidX JUnit** (1.1.5) - Android testing extensions
- **Espresso Core** (3.5.1) - UI testing framework
