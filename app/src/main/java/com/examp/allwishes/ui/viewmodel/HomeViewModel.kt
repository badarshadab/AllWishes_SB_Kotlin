package com.examp.allwishes.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.examp.allwishes.ui.model.Root_Hl
import com.examp.allwishes.ui.util.Resource
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val myRepository: MyRepository) : ViewModel() {

    var listMainModel: MutableLiveData<Resource<List<Root_Hl>>>? = null
    var listStorage: MutableLiveData<Resource<List<StorageReference>>>? = null
    var categoryName: String = ""

    fun loadRealTimeData(): MutableLiveData<Resource<List<Root_Hl>>> {
        if (listMainModel == null) {
            listMainModel = loadRealTimeDataOnce() as MutableLiveData<Resource<List<Root_Hl>>>
        }
        return listMainModel as MutableLiveData<Resource<List<Root_Hl>>>
    }

    private fun loadRealTimeDataOnce() = liveData(Dispatchers.IO) {
        println(" CategoryList loadRealTimeDataOnce ")
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = myRepository.loadRealTimeData()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun loadImagesStorage(catName: String): MutableLiveData<Resource<List<StorageReference>>> {
        println("catName " + catName)
        if (!catName.equals(categoryName)) {
            listStorage =
                loadImagesStorageOnce(catName) as MutableLiveData<Resource<List<StorageReference>>>
        }
        return listStorage as MutableLiveData<Resource<List<StorageReference>>>
    }

    private fun loadImagesStorageOnce(catName: String) = liveData(Dispatchers.IO) {
        categoryName = catName
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = myRepository.loadImagesStorage(catName)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}