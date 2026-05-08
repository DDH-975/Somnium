package com.project.somnium.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.somnium.data.local.DiaryDataClass
import com.project.somnium.data.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadDiaryViewModel @Inject constructor(private val repo: DiaryRepository) : ViewModel() {
    fun selectById(id: Int): LiveData<DiaryDataClass> {
        return repo.selectByID(id)
    }
}