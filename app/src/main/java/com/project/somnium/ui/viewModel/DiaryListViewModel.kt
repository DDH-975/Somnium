package com.project.somnium.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.somnium.data.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryListViewModel @Inject constructor(
    private val repo: DiaryRepository
) : ViewModel() {
    val allData = repo.getAllData()

    fun deleteByTile(title: String) {
        viewModelScope.launch {
            repo.deleteByTile(title)
        }
    }
}