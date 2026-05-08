package com.project.somnium.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.somnium.data.local.DiaryDataClass
import com.project.somnium.data.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repo: DiaryRepository) : ViewModel() {
    private val _thumbnailData: LiveData<List<DiaryDataClass>> = repo.getDataDesc()
    val thumbnailData: LiveData<List<DiaryDataClass>> = _thumbnailData
}