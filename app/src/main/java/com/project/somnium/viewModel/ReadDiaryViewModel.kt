package com.project.somnium.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.somnium.diaryDb.DiaryDataClass
import com.project.somnium.diaryDb.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadDiaryViewModel @Inject constructor(private val repo: DiaryRepository) : ViewModel() {
    fun selectById(id: Int): LiveData<DiaryDataClass> {
        return repo.selectByID(id)
    }
}