package com.project.somnium.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diaryDb.DiaryRepository
import kotlinx.coroutines.launch

class DiaryListViewModel(application: Application) : AndroidViewModel(application) {
    private val db = DataBase.getDatabase(application)
    private val diaryDao = db.DiaryDataDao()
    private val repo = DiaryRepository(diaryDao)
    val allData = repo.getAllData()

    fun deleteByTile(title: String) {
        viewModelScope.launch {
            repo.deleteByTile(title)
        }
    }
}