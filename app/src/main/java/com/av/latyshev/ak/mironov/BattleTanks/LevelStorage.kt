package com.av.latyshev.ak.mironov.BattleTanks

import android.app.Activity
import android.content.Context
import com.av.latyshev.ak.mironov.BattleTanks.models.Element
import com.google.gson.Gson
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken

const val KEY_LEVEL = "key_level"

class LevelStorage(val context: Context) {
    private val prefs = (context as Activity).getPreferences(Context.MODE_PRIVATE)

    fun saveLevel(elementsOnContainer: List<Element>) {
        prefs.edit() {
            putString(KEY_LEVEL, Gson().toJson(elementsOnContainer))
            apply()
        }
    }

    fun loadLevel(): List<Element>? {
        val levelFromPrefs = prefs.getString(KEY_LEVEL, null)
        levelFromPrefs.let {
            val type = object : TypeToken<List<Element>>() {}.type
            return Gson().fromJson(it, type)
        }
        return null
    }
}