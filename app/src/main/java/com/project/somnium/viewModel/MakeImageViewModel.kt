package com.project.somnium.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.somnium.makeImg_Recycler.RecyclerDataModel
import com.project.somnium.retrofit.GptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakeImageViewModel @Inject constructor(
    private val repository: GptRepository
) : ViewModel() {
    private val _imageData = MutableLiveData<RecyclerDataModel>()
    val imageData: LiveData<RecyclerDataModel> = _imageData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun generateImage(prompt: String) {
        viewModelScope.launch {
            val result = repository.requestImage(prompt)

            result.onSuccess {
                _imageData.value = it
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }
}