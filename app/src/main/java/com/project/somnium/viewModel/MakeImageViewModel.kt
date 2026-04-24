package com.project.somnium.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.somnium.makeImg_Recycler.RecyclerDataModel
import com.project.somnium.retrofit.GptRepository
import kotlinx.coroutines.launch

class MakeImageViewModel : ViewModel() {
    private val repository = GptRepository()
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