package com.soundnote.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.soundnote.presentation.ui.library.LibraryScreen
import com.soundnote.presentation.ui.player.PlayerScreen
import com.soundnote.presentation.ui.recording.RecordingScreen
import com.soundnote.presentation.ui.settings.SettingsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Recording.route,
        modifier = modifier
    ) {
        composable(Screen.Recording.route) {
            RecordingScreen(
                onNavigateToLibrary = { navController.navigate(Screen.Library.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToPlayer = { id -> navController.navigate(Screen.Player.createRoute(id)) }
            )
        }
        composable(Screen.Library.route) {
            LibraryScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPlayer = { id -> navController.navigate(Screen.Player.createRoute(id)) }
            )
        }
        composable(
            route = Screen.Player.route,
            arguments = listOf(navArgument("recordingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recordingId = backStackEntry.arguments?.getString("recordingId") ?: return@composable
            PlayerScreen(
                recordingId = recordingId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
