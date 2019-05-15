package agh.vote7.utils

import android.content.Context
import androidx.annotation.StringRes

class StringRepository(
    private val applicationContext: Context
) {
    fun getString(@StringRes resId: Int): String = applicationContext.getString(resId)
}