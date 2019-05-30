package com.samuelcabezas.rmoviesapp.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.samuelcabezas.rmoviesapp.models.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
