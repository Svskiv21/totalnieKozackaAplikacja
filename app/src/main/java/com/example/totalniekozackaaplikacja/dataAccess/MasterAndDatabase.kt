package com.example.totalniekozackaaplikacja.dataAccess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.totalniekozackaaplikacja.dataAccess.dao.PlayerDao
import com.example.totalniekozackaaplikacja.dataAccess.dao.PlayerWithScoresDao
import com.example.totalniekozackaaplikacja.dataAccess.dao.ScoreDao
import com.example.totalniekozackaaplikacja.model.Player
import com.example.totalniekozackaaplikacja.model.Score

@Database(
    entities = [Player::class, Score::class],
    version = 1,
    exportSchema = false
)
abstract class MasterAndDatabase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun scoreDao(): ScoreDao
    abstract fun playerWithScoresDao(): PlayerWithScoresDao
}