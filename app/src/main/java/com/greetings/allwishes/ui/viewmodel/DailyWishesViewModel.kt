package com.greetings.allwishes.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greetings.allwishes.ui.model.Root_HlNew
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DailyWishesViewModel : ViewModel() {

    val repositoryResponseLiveData = MutableLiveData<Root_HlNew?>()

    init {
        getComModel()
    }

    private fun getComModel() {
        val database: DatabaseReference = Firebase.database.reference
        GlobalScope.launch(Dispatchers.IO) {
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        repositoryResponseLiveData.value =
                            dataSnapshot.getValue(Root_HlNew::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Shadab  Database from fiebase" + databaseError.message)
                }
            }
            database.addValueEventListener(postListener)
        }
    }


}