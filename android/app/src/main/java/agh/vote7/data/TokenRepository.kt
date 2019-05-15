package agh.vote7.data

import android.content.Context

class TokenRepository(
    applicationContext: Context
) {
    private val sharedPreferences = applicationContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    var token: String? = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) = synchronized(this) {
            field = value
            sharedPreferences.edit()
                .putString(KEY_TOKEN, value)
                .apply()
        }

    companion object {
        private const val PREFERENCES = "vote7.token"
        private const val KEY_TOKEN = "token"
    }
}
