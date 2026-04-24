package com.project.somnium.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diaryDb.DiaryRepository

class WriteDiaryViewModel(application: Application): AndroidViewModel(application) {
    private val db = DataBase.getDatabase(application)
    private val diaryDao = db.DiaryDataDao()
    private val repo = DiaryRepository(diaryDao)
}