package com.example.totalniekozackaaplikacja.dataAccess.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.totalniekozackaaplikacja.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addPlayer(playerEntity: Player): Long

    @Query("Select * from `players`") // PAMIETAJ TYLDY NIE APOSTROFY DO NAZWY TABELI
    abstract fun getAllPlayers(): Flow<List<Player>>

    @Query("select * from `players` where email = :email")
    abstract fun getPlayerByEmail(email: String): Flow<Player>

    @Update
    abstract suspend fun updatePlayer(playerEntity: Player)

    @Query("Select * from `players` where player_id=:id")
    abstract fun getPlayerById(id: Long): Flow<Player>
}