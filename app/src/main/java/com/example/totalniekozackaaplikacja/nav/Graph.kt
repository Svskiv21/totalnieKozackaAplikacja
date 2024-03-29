package com.example.totalniekozackaaplikacja.nav

import ProfileScreen
import com.example.totalniekozackaaplikacja.widoki.GameMainScreen
import com.example.totalniekozackaaplikacja.widoki.LoginScreen
import com.example.totalniekozackaaplikacja.widoki.ResultScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "login_screen",
    ) {
        composable(
            route = RoutesMap.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(
            route = RoutesMap.Profile.route + "/{playerId}/{colorNumber}?uri={uri}",
            arguments = listOf(
                navArgument("uri") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                },
                navArgument("playerId") {type = NavType.LongType},
                navArgument("colorNumber") {type = NavType.IntType}
            )) {
                backStackEntry ->
            val uri = backStackEntry.arguments?.getString("uri")
            val playerId = backStackEntry.arguments?.getLong("playerId")!!
            val number = backStackEntry.arguments?.getInt("colorNumber")!!
            ProfileScreen(
                navController = navController,
                uri = uri,
                playerId = playerId,
                number = number
            )
        }
        composable(
            route = RoutesMap.Game.route + "/{playerId}/{colorNumber}?uri={uri}",
            arguments = listOf(
                navArgument("colorNumber") {type = NavType.IntType},
                navArgument("playerId") {type = NavType.LongType},
                navArgument("uri") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            )) {
                backStackEntry ->
            val number = backStackEntry.arguments?.getInt("colorNumber")!!
            val playerId = backStackEntry.arguments?.getLong("playerId")!!
            val uri = backStackEntry.arguments?.getString("uri")
            GameMainScreen(
                navController = navController,
                number = number,
                playerId = playerId,
                uri = uri)
        }
        composable( // backStackEntry
            route = RoutesMap.Results.route + "/{playerId}/{colorNumber}/{recentScore}?uri={uri}",
            arguments = listOf(
                navArgument("playerId") { type = NavType.LongType },
                navArgument("colorNumber") { type = NavType.IntType },
                navArgument("recentScore") {type = NavType.IntType},
                navArgument("uri") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            )) {
                backStackEntry ->
            val playerId = backStackEntry.arguments?.getLong("playerId")!!
            val colorNumber = backStackEntry.arguments?.getInt("colorNumber")!!
            val recentScore = backStackEntry.arguments?.getInt("recentScore")!!
            val uri = backStackEntry.arguments?.getString("uri")
            ResultScreen(
                navController = navController,
                profileId = playerId,
                colorNumber = colorNumber,
                recentScore = recentScore,
                uri = uri
            )
        }
    }
}