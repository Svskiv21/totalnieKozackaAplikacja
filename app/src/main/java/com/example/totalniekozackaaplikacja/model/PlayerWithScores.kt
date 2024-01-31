package com.example.totalniekozackaaplikacja.model

import androidx.room.Embedded
import androidx.room.Relation
data class PlayerWithScores (
    val player_id: Long,
    val score_id: Long,
    val points: Int,
    val name: String,
    val email: String
)