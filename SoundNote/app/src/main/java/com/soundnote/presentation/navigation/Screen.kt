package com.soundnote.presentation.navigation

sealed class Screen(val route: String) {
    data object Recording : Screen("recording")
    data object Library : Screen("library")
    data object Player : Screen("player/{recordingId}") {
        fun createRoute(recordingId: String) = "player/$recordingId"
    }
    data object Settings : Screen("settings")
}
