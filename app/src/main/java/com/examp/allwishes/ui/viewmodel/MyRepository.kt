package com.examp.allwishes.ui.viewmodel

import com.examp.allwishes.ui.data.api.FirebaseHelper

class MyRepository(private val firebaseHelper: FirebaseHelper) {

    suspend fun loadRealTimeData() = firebaseHelper.loadRealTimeData()

    suspend fun loadImagesStorage(catName: String) = firebaseHelper.loadImagesStorage(catName)

}