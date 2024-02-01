package com.example.totalniekozackaaplikacja.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.totalniekozackaaplikacja.dataAccess.repos.PlayersWithScoresRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PlayerWithScoresViewModel(private val playerWithScoresRepository: PlayersWithScoresRepository): ViewModel() {
    val playerWithScore = playerWithScoresRepository
        .getAllPlayerWithScores()
        .stateIn( // to z wykladu wziete
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}