package com.example.totalniekozackaaplikacja.dataAccess.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.totalniekozackaaplikacja.model.Score

@Dao
abstract class ScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertScore(score: Score)
}