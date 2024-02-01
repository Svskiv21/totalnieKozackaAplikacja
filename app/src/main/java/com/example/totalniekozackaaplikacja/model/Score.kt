package com.example.totalniekozackaaplikacja.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score (
    @PrimaryKey(autoGenerate = true)
    val score_id: Long = 0,
    val player_id: Long,
    val points: Int)

