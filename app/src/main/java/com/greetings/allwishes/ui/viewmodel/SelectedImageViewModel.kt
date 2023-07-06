package com.greetings.allwishes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greetings.allwishes.ui.data.api.FirebaseHelper
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch


class SelectedImageViewModel : ViewModel() {
    private var stickerJsonData: MutableLiveData<List<StorageReference>>? = null
    fun getAllImage(): LiveData<List<StorageReference>>? {
        if (stickerJsonData == null) {
            stickerJsonData = MutableLiveData<List<StorageReference>>()
            loadImages()
        }
        return stickerJsonData
    }

    fun loadImages() {
        val firebaseHelper = FirebaseHelper()
        viewModelScope.launch {
            stickerJsonData?.value = firebaseHelper.loadImagesStorage("Stickers")
        }
//        val api = RetrofitClient.getRetrofitInstance()!!.create(ApiInterface::class.java)
//        val call = api.getAllPhotos()
//        call.enqueue(object : retrofit2.Callback<String> {
//            override fun onResponse(
//                call: Call<String>,
//                response: Response<String>
//            ) {
////                val gson = Gson()
//                val jsonValue: String? = response.body().toString()
//                stickerJsonData?.value = jsonValue
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                println("onFailure")
//            }
//        })
    }
}