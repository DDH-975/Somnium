package com.project.somnium.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diaryDb.DiaryDataClass
import com.project.somnium.diaryDb.DiaryRepository

class ReadDiaryViewModel(application: Application): AndroidViewModel(application) {
    val db = DataBase.getDatabase(application)
    val diaryDao = db.DiaryDataDao()
    val repo = DiaryRepository(diaryDao)

    lateinit var _selectByIdData: LiveData<DiaryDataClass>

    fun selectById(id: Int) { _selectByIdData = repo.selectByID(id) }
}