package com.example.totalniekozackaaplikacja.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.totalniekozackaaplikacja.MasterAnd

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            PlayerViewModel(masterAndApplication().container.playerRepository)
        }
        initializer {
            ScoreViewModel(masterAndApplication().container.scoreRepository)
        }
        initializer {
            PlayerWithScoresViewModel(masterAndApplication().container.playerWithScoresRepository)
        }
    }
}

fun CreationExtras.masterAndApplication(): MasterAnd = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MasterAnd
        )