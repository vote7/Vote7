package agh.vote7

import agh.vote7.utils.DependencyProvider
import android.app.Application
import timber.log.Timber

@Suppress("unused")
class VoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        DependencyProvider.applicationContext = this
    }
}
