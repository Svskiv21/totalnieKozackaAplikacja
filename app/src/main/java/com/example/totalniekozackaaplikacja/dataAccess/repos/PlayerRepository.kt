package com.example.totalniekozackaaplikacja.dataAccess.repos

import com.example.totalniekozackaaplikacja.dataAccess.dao.PlayerDao
import com.example.totalniekozackaaplikacja.model.Player
import kotlinx.coroutines.flow.Flow

class PlayerRepository(private val playerDao: PlayerDao) {
    suspend fun addPlayer(player: Player) : Long{ // suspend jest do asynchronicznego wykonywania się funkcji
        return playerDao.addPlayer(player)
    }

    fun getAllPlayers(): Flow<List<Player>> = playerDao.getAllPlayers() // Flow ma juz w sobie coroutines, wiec nie trzeba dawac suspend

    fun getPlayerById(id: Long): Flow<Player> {
        return playerDao.getPlayerById(id)
    }

    fun getPlayerByEmail(email: String): Flow<Player> {
        return playerDao.getPlayerByEmail(email)
    }

    suspend fun updatePlayer(player: Player){
        playerDao.updatePlayer(player)
    }
}