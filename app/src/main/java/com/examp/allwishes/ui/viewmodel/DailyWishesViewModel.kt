package com.examp.allwishes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examp.allwishes.ui.model.Root_Hl
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

    private var comModel: MutableLiveData<Root_Hl?>? = null

    fun getComModel(): LiveData<Root_Hl?> {
        if (comModel == null) {
            comModel = MutableLiveData()
            loadCommonData()
        }
        return comModel as MutableLiveData<Root_Hl?>
    }

    private fun loadCommonData() {
        val database: DatabaseReference = Firebase.database.reference
        GlobalScope.launch(Dispatchers.IO) {
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        comModel?.value = dataSnapshot.getValue(Root_Hl::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Anshu  Database from fiebase" + databaseError.message)
                }
            }
            database.addValueEventListener(postListener)
        }
    }


}