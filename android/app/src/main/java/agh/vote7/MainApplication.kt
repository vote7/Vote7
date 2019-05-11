package agh.vote7

import agh.vote7.login.data.model.LoggedInUser
import android.annotation.SuppressLint
import android.app.Application

@SuppressLint("Registered")
class MainApplication : Application() {
    companion object {
        lateinit var token : String
        lateinit var loggedInUser : LoggedInUser
    }
}