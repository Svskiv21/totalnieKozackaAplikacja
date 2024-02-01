package com.example.totalniekozackaaplikacja

import android.app.Application

class MasterAndApplication: Application(){
    lateinit var container: AppDataContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}