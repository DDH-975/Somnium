package com.project.somnium.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project.somnium.diaryDb.DataBase
import com.project.somnium.diaryDb.DiaryDataClass
import com.project.somnium.diaryDb.DiaryRepository
import kotlinx.coroutines.launch

class WriteDiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = DataBase.getDatabase(application)
    private val diaryDao = db.DiaryDataDao()
    private val repo = DiaryRepository(diaryDao)

    lateinit var _selectByIdData: LiveData<DiaryDataClass>

    fun insertData(data: DiaryDataClass) {
        viewModelScope.launch {
            repo.insertData(data)
        }
    }

    fun updateById(id: Int, title: String, content: String, imgUrl: String) {
        viewModelScope.launch {
            repo.updateByID(id, title, content, imgUrl)
        }
    }

    fun selectById(id: Int) { _selectByIdData = repo.selectByID(id) }
}