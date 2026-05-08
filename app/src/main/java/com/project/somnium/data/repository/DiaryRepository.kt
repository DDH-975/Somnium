package com.project.somnium.data.repository

import androidx.lifecycle.LiveData
import com.project.somnium.data.local.DiaryDao
import com.project.somnium.data.local.DiaryDataClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DiaryRepository @Inject constructor(private val dao: DiaryDao) {
    fun getDataDesc(): LiveData<List<DiaryDataClass>> = dao.getDataDesc()
    fun selectByID(id: Int): LiveData<DiaryDataClass> = dao.selectByID(id)
    fun getAllData(): LiveData<List<DiaryDataClass>> = dao.getAllData()

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

    suspend fun deleteByTile(title: String) {
        withContext(Dispatchers.IO) {
            dao.deleteByTile(title)
        }
    }
}