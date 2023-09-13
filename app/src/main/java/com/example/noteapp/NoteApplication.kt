package com.example.noteapp

import android.app.Application
import com.example.noteapp.di.AppContainer
import com.example.noteapp.di.AppDataContainer

class NoteApplication: Application() {
    lateinit var conatiner: AppContainer

    override fun onCreate() {
        super.onCreate()
        conatiner = AppDataContainer(this)
    }
}