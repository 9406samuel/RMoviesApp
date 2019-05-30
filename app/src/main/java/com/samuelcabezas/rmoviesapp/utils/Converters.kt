package com.samuelcabezas.rmoviesapp.utils

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters{
    @TypeConverter
    fun restoreIntList(listOfString: String): List<Int> {
        return Gson().fromJson(listOfString, object : TypeToken<List<Int>>() {

        }.type)
    }

    @TypeConverter
    fun saveIntList(listOfString: List<Int>): String {
        return Gson().toJson(listOfString)
    }
}