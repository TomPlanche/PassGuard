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

| File | Description |
|------|-------------|
| `MainActivity.kt` | Main activity with Jetpack Compose setup, bottom navigation bar, and app scaffold |
| `Screen.kt` | Sealed class defining navigation routes for all screens |
| `PassGuardNavHost.kt` | Navigation host composable managing screen transitions |
| `PasswordGeneratorScreen.kt` | Screen for generating passwords with configurable rules |
| `PasswordCheckerScreen.kt` | Screen for validating passwords against rules |
| `RuleProfilesScreen.kt` | Screen listing all saved rule profiles |
| `EditRuleScreen.kt` | Screen for creating or editing rule profiles |

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

- **View**: Composable screens in `ui/screens/`
- **ViewModel**: (To be implemented) Business logic and state management
- **Model**: (To be implemented) Data classes and repository layer

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

- **Jetpack Compose** - Modern declarative UI toolkit
- **Material 3** - Material Design 3 components
- **Navigation Compose** - Type-safe navigation for Compose
- **AndroidX Core KTX** - Kotlin extensions for Android APIs
- **Lifecycle Runtime KTX** - Lifecycle-aware components
