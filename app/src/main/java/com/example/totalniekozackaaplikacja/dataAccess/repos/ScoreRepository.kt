package com.example.totalniekozackaaplikacja.dataAccess.repos

import com.example.totalniekozackaaplikacja.dataAccess.dao.ScoreDao
import com.example.totalniekozackaaplikacja.model.Score

class ScoreRepository(private val scoreDao: ScoreDao) {
    suspend fun insertScore(score: Score){
        scoreDao.insertScore(score)
    }

}