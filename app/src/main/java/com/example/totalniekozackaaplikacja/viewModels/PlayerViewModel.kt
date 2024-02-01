package com.example.totalniekozackaaplikacja.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.totalniekozackaaplikacja.dataAccess.repos.PlayerRepository
import com.example.totalniekozackaaplikacja.model.Player
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlayerViewModel(private val playerRepository: PlayerRepository): ViewModel() {
    var playerId by mutableStateOf(0L)
        private set
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    fun updatePlayerId(newPlayerId: Long) {
        playerId = newPlayerId
    }

    fun updateName(newName: String) {
        name = newName
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    suspend fun savePlayer() {
        try {
            val player = playerRepository.getPlayerByEmail(email).first()
            val playerToUpdate = Player(player.player_id, player.name, player.email)
            playerRepository.updatePlayer(playerToUpdate)
            playerId = player.player_id
        } catch (npe: NullPointerException) {
            val player = Player(name = name, email = email)
            playerId = playerRepository.addPlayer(player)
        }
    }

    fun getAllPlayers(): List<Player> {
        var allPlayers : List<Player> = emptyList()
        viewModelScope.launch {
            val allPlayersStream = playerRepository.getAllPlayers()
            allPlayers = allPlayersStream.first()
        }
        return allPlayers
    }

    suspend fun getPlayerById(playerId: Long) {
        val player = playerRepository.getPlayerById(playerId).first()
        this.playerId = player.player_id
        this.name = player.name
        this.email = player.email
    }
}