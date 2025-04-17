package com.project.somnium.diaryDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: DiaryDataClass)

    @Query("select * from DiaryDB")
    suspend fun getAllData(): List<DiaryDataClass>

    @Query("delete from DiaryDB")
    suspend fun deleteAllData()

    @Query("select * from DiaryDB where title = :title")
    suspend fun getDataByTitle(title: String): DiaryDataClass

    @Query("delete from DiaryDB where title = :title")
    suspend fun deleteByTile(title: String)

    @Query("update DiaryDB set title = :title, content = :content, imgurl = :imgUrl   where id = :id ")
    suspend fun updateByID(id: Int, title: String, content: String, imgUrl: String)

    @Query("select * from DiaryDB where id = :id")
    suspend fun selectByID(id: Int) : DiaryDataClass
}