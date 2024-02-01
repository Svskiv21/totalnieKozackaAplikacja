package com.example.totalniekozackaaplikacja.nav

sealed class RoutesMap(val route: String){
    object Login : RoutesMap(route = "login_screen")
    object Profile: RoutesMap(route = "profile_screen")
    object Game : RoutesMap(route = "game_screen")
    object Results : RoutesMap(route = "results_screen")
}