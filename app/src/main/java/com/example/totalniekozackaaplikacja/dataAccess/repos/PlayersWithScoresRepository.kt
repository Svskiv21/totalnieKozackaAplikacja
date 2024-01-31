package com.example.totalniekozackaaplikacja.dataAccess.repos

import com.example.totalniekozackaaplikacja.dataAccess.dao.PlayerWithScoresDao
import com.example.totalniekozackaaplikacja.model.PlayerWithScores
import kotlinx.coroutines.flow.Flow

class PlayersWithScoresRepository(private val playerWithScoresDao: PlayerWithScoresDao) {
    fun getAllPlayerWithScores(): Flow<List<PlayerWithScores>> {
        return playerWithScoresDao.getPlayersWithScore()
    }
}