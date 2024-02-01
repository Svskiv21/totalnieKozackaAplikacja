package com.example.totalniekozackaaplikacja

import android.content.Context
import com.example.totalniekozackaaplikacja.dataAccess.MasterAndDatabase
import com.example.totalniekozackaaplikacja.dataAccess.repos.PlayerRepository
import com.example.totalniekozackaaplikacja.dataAccess.repos.PlayersWithScoresRepository
import com.example.totalniekozackaaplikacja.dataAccess.repos.ScoreRepository

interface AppContainer {
    val playerRepository: PlayerRepository
    val scoreRepository: ScoreRepository
    val playerWithScoresRepository: PlayersWithScoresRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val playerRepository: PlayerRepository by lazy {
        PlayerRepository(MasterAndDatabase.getDatabase(context).playerDao())
    }
    override val scoreRepository: ScoreRepository by lazy {
        ScoreRepository(MasterAndDatabase.getDatabase(context).scoreDao())
    }
    override val playerWithScoresRepository: PlayersWithScoresRepository by lazy {
        PlayersWithScoresRepository(MasterAndDatabase.getDatabase(context).playerWithScoresDao())
    }
}