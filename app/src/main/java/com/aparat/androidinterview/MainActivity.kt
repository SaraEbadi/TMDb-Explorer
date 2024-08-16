package com.aparat.androidinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.aparat.androidinterview.presentation.movies.navigateToMovies
import com.aparat.androidinterview.presentation.tvshows.navigateToTvShow
import com.aparat.androidinterview.systemdesign.navigation.TMDbNavHost
import com.aparat.androidinterview.systemdesign.navigation.TopLevelDestination
import com.aparat.androidinterview.systemdesign.theme.AparatAndroidInterviewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AparatAndroidInterviewTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomBar(navController = navController)
                }) { innerPadding ->
                    TMDbNavHost(navController, innerPadding)
                }
            }
        }
    }

    @Composable
    fun BottomBar(navController: NavHostController) {
        var selectedItem by rememberSaveable { mutableIntStateOf(0) }
        val backStackEntry by navController.currentBackStackEntryAsState()
        val route = backStackEntry?.destination?.route ?: ""
        selectedItem = when {
            route.contains("movies" , ignoreCase = true) -> 0
            route.contains("series" , ignoreCase = true) -> 1
            else -> -1
        }
        NavigationBar {
            TopLevelDestination.entries.forEachIndexed { index, screen ->
                val selected = selectedItem == index
                NavigationBarItem(label = {
                    Text(text = screen.iconText, fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal) },
                    selected = selected,
                    onClick = { navigateToBottomNavDestination(navController, screen) },
                    icon = {
                        Icon(imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon, contentDescription = "Navigation Icon")
                    })
            }
        }
    }

    private fun navigateToBottomNavDestination(navController: NavHostController, topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when(topLevelDestination){
            TopLevelDestination.MOVIES -> navController.navigateToMovies(topLevelNavOptions)
            TopLevelDestination.SHOWS -> navController.navigateToTvShow(topLevelNavOptions)
            else -> {}
        }
    }
}
