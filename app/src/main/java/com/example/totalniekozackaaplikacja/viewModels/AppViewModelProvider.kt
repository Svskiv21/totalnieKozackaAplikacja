package com.example.totalniekozackaaplikacja.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.totalniekozackaaplikacja.MasterAndApplication

// ze skryptu
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { PlayerViewModel(masterAnd().container.playerRepository) }
        initializer { ScoreViewModel(masterAnd().container.scoreRepository) }
        initializer { PlayerWithScoresViewModel(masterAnd().container.playerWithScoresRepository) }
    }
}
// ze skryptu
// udostepnia niestandardowy obiekt aplikacji i posrednio pojemnik w ktorym przechowywane są obiekty repozytorium
fun CreationExtras.masterAnd(): MasterAndApplication = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MasterAndApplication
        )