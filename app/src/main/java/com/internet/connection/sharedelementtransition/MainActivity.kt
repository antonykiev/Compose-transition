package com.internet.connection.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.internet.connection.sharedelementtransition.ui.theme.SharedElementTransitionTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedElementTransitionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SharedTransitionLayout {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "list",
                        ) {
                            composable("list") {
                                ListScreen(
                                    onItemClick = { resId, text ->
                                        navController.navigate("detail/$resId/$text")
                                    },
                                    animatedVisibilityScope = this
                                )
                            }
                            composable(
                                route = "detail/{resId}/{text}",
                                arguments = listOf(
                                    navArgument("resId") { type = NavType.IntType },
                                    navArgument("text") { type = NavType.StringType }
                                )) { backStackEntry ->
                                val resId = backStackEntry.arguments?.getInt("resId") ?: 0
                                val text = backStackEntry.arguments?.getString("text").orEmpty()
                                DetailScreen(
                                    resId = resId,
                                    text = text,
                                    animatedVisibilityScope = this
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}