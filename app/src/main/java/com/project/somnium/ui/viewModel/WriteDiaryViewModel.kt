package com.project.somnium.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.somnium.data.local.DiaryDataClass
import com.project.somnium.data.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteDiaryViewModel @Inject constructor(
    private val repo: DiaryRepository
) : ViewModel() {

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

    fun selectById(id: Int): LiveData<DiaryDataClass> {
        return repo.selectByID(id)
    }
}