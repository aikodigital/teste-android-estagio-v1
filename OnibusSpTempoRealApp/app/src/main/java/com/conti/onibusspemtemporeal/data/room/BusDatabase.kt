package com.conti.onibusspemtemporeal.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.conti.onibusspemtemporeal.data.models.BusRoute

@Database(
    entities = [BusRoute::class],
    version = 1
)
abstract class BusDatabase : RoomDatabase() {

    abstract fun busDao(): BusDao
}