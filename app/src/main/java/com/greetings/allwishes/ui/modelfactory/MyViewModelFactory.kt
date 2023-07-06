package com.greetings.allwishes.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greetings.allwishes.ui.data.api.FirebaseHelper
import com.greetings.allwishes.ui.viewmodel.HomeViewModel
import com.greetings.allwishes.ui.viewmodel.MyRepository

class MyViewModelFactory(private val firebaseHelper: FirebaseHelper) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(MyRepository(firebaseHelper)) as T
//        }
//        throw IllegalArgumentException("Unknown class name")
//    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(MyRepository(firebaseHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}