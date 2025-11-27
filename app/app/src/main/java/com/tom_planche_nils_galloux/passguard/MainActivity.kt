package com.tom_planche_nils_galloux.passguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tom_planche_nils_galloux.passguard.data.local.ProfileDataStore
import com.tom_planche_nils_galloux.passguard.data.repository.ProfileRepository
import com.tom_planche_nils_galloux.passguard.data.repository.ProfileRepositoryImpl
import com.tom_planche_nils_galloux.passguard.ui.navigation.PassGuardNavHost
import com.tom_planche_nils_galloux.passguard.ui.navigation.Screen
import com.tom_planche_nils_galloux.passguard.ui.theme.PassGuardTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataStore = ProfileDataStore(applicationContext)
        val repository: ProfileRepository = ProfileRepositoryImpl(dataStore)

        CoroutineScope(Dispatchers.IO).launch {
            repository.initializeDefaultProfiles()
        }

        setContent {
            PassGuardTheme {
                PassGuardApp(repository = repository)
            }
        }
    }
}

@Composable
fun PassGuardApp(repository: ProfileRepository) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem(
            screen = Screen.Generator,
            label = "Generator",
            icon = Icons.Default.Lock
        ),
        BottomNavItem(
            screen = Screen.Checker,
            label = "Checker",
            icon = Icons.Default.Check
        ),
        BottomNavItem(
            screen = Screen.Profiles,
            label = "Profiles",
            icon = Icons.Default.Settings
        )
    )

    // Hide bottom nav on EditRule screen
    val showBottomNav = currentDestination?.route?.startsWith("edit_rule") != true

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomNav) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == item.screen.route
                            } == true,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        PassGuardNavHost(
            navController = navController,
            repository = repository,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

private data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)
