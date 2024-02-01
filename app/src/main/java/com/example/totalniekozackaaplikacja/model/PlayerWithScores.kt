package com.example.totalniekozackaaplikacja.model

data class PlayerWithScores (
    val player_id: Long,
    val score_id: Long,
    val points: Int,
    val name: String,
    val email: String
)