package com.greetings.allwishes.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

class HomeViewModel(private val myRepository: MyRepository) : ViewModel() {

    val repositoryResponseLiveData_ImageStore = MutableLiveData<List<StorageReference>>()

    fun loadImagesStorage(catName: String) {
        viewModelScope.launch {
            repositoryResponseLiveData_ImageStore.value = myRepository.loadImagesStorage(catName)
        }
    }

}