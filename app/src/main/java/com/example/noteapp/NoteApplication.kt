package com.example.noteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp//annotation that makes the app call this class first
class NoteApplication: Application()