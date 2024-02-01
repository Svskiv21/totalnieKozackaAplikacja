package com.example.totalniekozackaaplikacja.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.totalniekozackaaplikacja.dataAccess.repos.ScoreRepository
import com.example.totalniekozackaaplikacja.model.Score

class ScoreViewModel(private val scoreRepository: ScoreRepository): ViewModel() {
    var playerId by mutableStateOf(0L)
    var points by mutableStateOf(1)
        private set

    suspend fun insertScore() {
        scoreRepository.insertScore(Score(player_id = playerId, points = points))
    }

    fun updatePoints(newPoints: Int) {
        points = newPoints
    }
}