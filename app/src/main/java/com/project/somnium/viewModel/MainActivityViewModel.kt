package com.project.somnium.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diaryDb.DiaryDataClass
import com.project.somnium.diaryDb.DiaryRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val db: DataBase = DataBase.getDatabase(getApplication())
    private val diaryDao = db.DiaryDataDao()
    private val repo = DiaryRepository(diaryDao)
    private val _thumbnailData: LiveData<List<DiaryDataClass>> = repo.getDataDesc()
    val thumbnailData: LiveData<List<DiaryDataClass>> = _thumbnailData
}