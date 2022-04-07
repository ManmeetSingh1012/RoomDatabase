package com.example.roomdatabase

import android.app.Application

class StudentApp:Application() {

    // intializing data base
    // and reciving the database
    val db by lazy {
        StudentDatabase.getInstance(this)
    }
}