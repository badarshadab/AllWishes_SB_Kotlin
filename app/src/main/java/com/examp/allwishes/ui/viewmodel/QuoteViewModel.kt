package com.examp.allwishes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.json.JSONArray
import java.io.File

class QuoteViewModel : ViewModel() {

    private var quotes = MutableLiveData<List<String>>()

    fun getData(catName: String): LiveData<List<String>> {
        getQuotes(catName)
        println("folder name is  " + catName)
        return quotes
    }

    fun getQuotes(catName: String) {

        println("path is " + catName)
        val storage = Firebase.storage
        val listRef = storage.reference.child(catName)
        listRef.listAll()
            .addOnSuccessListener { listResult ->
                val localFile = File.createTempFile("Quotes", "txt")
                if (listResult.items.isEmpty()) {
                    quotes.value = ArrayList()
                } else {
                    listResult.items[0].getFile(localFile).addOnSuccessListener {
                        val text = localFile.readText()
                        localFile.delete()
                        val array = JSONArray(text)
                        val list = ArrayList<String>()
                        for (i in 0 until array.length()) {
                            list.add(array[i].toString())
                        }
                        quotes.postValue(list)
                    }
                        .addOnFailureListener {
                            quotes.value = ArrayList()
                        }
                }
            }
            .addOnFailureListener {
                println("Exception is " + it)
                quotes.value = ArrayList()
            }
    }

}