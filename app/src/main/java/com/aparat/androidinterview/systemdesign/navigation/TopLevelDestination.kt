package com.aparat.androidinterview.systemdesign.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
){
    MOVIES(
        selectedIcon = Icons.Filled.Movie,
        unselectedIcon = Icons.Outlined.Movie,
        iconText = "Movies"
    ),
    SHOWS(
        selectedIcon = Icons.Filled.Tv,
        unselectedIcon = Icons.Outlined.Tv,
        iconText = "Shows"
    ),
    MORE(
        selectedIcon = Icons.Filled.Menu,
        unselectedIcon = Icons.Outlined.Menu,
        iconText = "More"
    )
}