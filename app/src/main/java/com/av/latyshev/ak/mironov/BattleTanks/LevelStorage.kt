package com.av.latyshev.ak.mironov.BattleTanks

import android.app.Activity
import android.content.Context
import com.av.latyshev.ak.mironov.BattleTanks.models.Element
import com.google.gson.Gson

const val KEY_LEVEL = "key_level"

class LevelStorage(val context: Context) {
    private val prefs = (context as Activity).getPreferences(Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveLevel(elementsOnContainer: List<Element>) {
        prefs.edit()
            .putString(KEY_LEVEL, gson.toJson(elementsOnContainer))
            .apply()
    }

    fun loadLevel(): List<Element>? {
        val levelFromPrefs = prefs.getString(KEY_LEVEL, null)?: return null
        val type= object : com.google.gson.reflect.TypeToken<List<Element>>() {}.type
        val elementsFromStorage: List<Element> = gson.fromJson(levelFromPrefs, type)
        val elementsWithNewIds = mutableListOf<Element>()
        elementsFromStorage.forEach {
            elementsWithNewIds.add(
                Element(
                    material = it.material,
                    coordinate = it.coordinate
                )
            )
        }
        return elementsFromStorage
    }
}