package com.example.totalniekozackaaplikacja.dataAccess.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.totalniekozackaaplikacja.model.PlayerWithScores
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PlayerWithScoresDao {

    @Query("select players.player_id as id_profile, `score_id` as id_score, `points` as points," +
            "players.name as name, players.email as email " +
            "from players, scores where players.player_id = scores.player_id")
    abstract fun getPlayersWithScore(): Flow<List<PlayerWithScores>>

}