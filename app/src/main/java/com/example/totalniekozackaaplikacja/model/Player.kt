package com.example.totalniekozackaaplikacja.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player (
    @PrimaryKey(autoGenerate = true)
    val player_id: Long = 0,
    val name: String,
    val email: String
)