package agh.vote7

import agh.vote7.login.data.model.LoggedInUser
import android.app.Application
import timber.log.Timber

class VoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var token: String? = null
        lateinit var loggedInUser: LoggedInUser
    }
}