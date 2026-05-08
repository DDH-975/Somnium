package com.project.somnium.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DiaryDataClass::class], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun DiaryDataDao(): DiaryDao
}