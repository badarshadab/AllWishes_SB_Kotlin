package com.hindishyari.shyari.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class GifsViewModel : ViewModel() {
    val storage = Firebase.storage

    private var cardList: MutableLiveData<List<StorageReference>>? = null
    var pathCategory: String = ""

    fun getCategoryWiseCards(path: String): MutableLiveData<List<StorageReference>> {
        if (pathCategory != path) {
            cardList = MutableLiveData()
            loadGif(path)
        }
        return cardList as MutableLiveData<List<StorageReference>>
    }

    private fun loadGif(path: String) {
        pathCategory = path
        cardList?.let { fetchCategoryWiseCard(it, "$path") }
    }

    fun fetchCategoryWiseCard(cardList: MutableLiveData<List<StorageReference>>, path: String) {
        val listRef = storage.reference.child(path)
        listRef.listAll()
            .addOnSuccessListener { listResult ->
                cardList?.postValue(listResult.items)
            }
            .addOnFailureListener { }
    }
}