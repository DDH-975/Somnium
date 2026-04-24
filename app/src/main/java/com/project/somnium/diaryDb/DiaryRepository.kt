package com.project.somnium.diaryDb

import androidx.lifecycle.LiveData

class DiaryRepository (private val dao: DiaryDao) {
    private lateinit var  thumbnailData: LiveData<List<DiaryDataClass>>

    fun getDataDesc(): LiveData<List<DiaryDataClass>>{
        thumbnailData = dao.getDataDesc()
        return dao.getDataDesc()
    }
}