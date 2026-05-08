package com.project.somnium.di

import android.content.Context
import androidx.room.Room
import com.project.somnium.data.local.DataBase
import com.project.somnium.data.local.DiaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext context: Context): DataBase{
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            "DiaryDB"
        ).build()
    }

    @Provides
    fun providesDao(dataBase: DataBase): DiaryDao{
        return dataBase.DiaryDataDao()
    }


}