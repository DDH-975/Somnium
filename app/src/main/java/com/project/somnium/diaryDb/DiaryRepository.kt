package com.project.somnium.diaryDb

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiaryRepository(private val dao: DiaryDao) {
    fun getDataDesc(): LiveData<List<DiaryDataClass>> = dao.getDataDesc()

    suspend fun insertData(data: DiaryDataClass) {
        withContext(Dispatchers.IO) {
            dao.insertData(data)
        }
    }

    suspend fun updateByID(id: Int, title: String, content: String, imgUrl: String) {
        withContext(Dispatchers.IO) {
            dao.updateByID(id, title, content, imgUrl)
        }
    }

    fun selectByID(id: Int): LiveData<DiaryDataClass> = dao.selectByID(id)
}