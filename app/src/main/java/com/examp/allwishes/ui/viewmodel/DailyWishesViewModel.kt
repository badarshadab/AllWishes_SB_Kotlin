package com.examp.allwishes.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examp.allwishes.ui.model.DailyWishe
import com.examp.allwishes.ui.model.EventByMonth
import com.examp.allwishes.ui.model.Root_Hl
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class DailyWishesViewModel : ViewModel() {

    private var comModel: MutableLiveData<Root_Hl>? = null
    private var arrayList: ArrayList<DailyWishe>? = null
    lateinit var dailylist: ArrayList<DailyWishe>
    private var _arrayListLiveData = MutableSharedFlow<ArrayList<DailyWishe>>()
    val arrayListLiveData: SharedFlow<ArrayList<DailyWishe>> = _arrayListLiveData

    fun getComModel(): ArrayList<DailyWishe>? {
        if (comModel == null) {
            comModel = MutableLiveData()
            loadCommonData()
            return null
        } else {
            return arrayList
        }
    }

    private fun loadCommonData() {
        val database: DatabaseReference = Firebase.database.reference
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val model = dataSnapshot.getValue(Root_Hl::class.java)
                    comModel?.value = model!!
                    arrayList?.clear()

                    dailylist = model.getDailyWishes()

                    viewModelScope.launch {
                        arrayList?.let { setArrayListLive(it) }
                    }
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

    private fun setArrayListLive(arrayList: ArrayList<DailyWishe>) {

        viewModelScope.launch {
            _arrayListLiveData?.emit(arrayList)
        }
    }


}