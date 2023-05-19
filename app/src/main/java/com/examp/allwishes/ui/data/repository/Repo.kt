package com.modlueinfotech.allwishesgif

import androidx.lifecycle.MutableLiveData
import com.modlueinfotech.allwishesgif.data.model.createCardMode.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object Repo {

//    fun loadData(homeModel : MutableLiveData<HomeModel>) {
//
//        val databasedata: DatabaseReference = Firebase.database.getReference("AllWishes")
//
//        val postList = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                try {
//                    homeModel.value = dataSnapshot.getValue(HomeModel::class.java)
//                    println("BIHAR2 $dataSnapshot")
//                }catch (e:Exception){
//                    e.printStackTrace()
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                println("error " + databaseError.message)
//            }
//        }
//    databasedata.addValueEventListener(postList)
//    }
//
//    val storage: FirebaseStorage = FirebaseStorage.getInstance()
//    fun fetchAllFrames(frameList : MutableLiveData<List<StorageReference>>,path: String){
//        val listRef = storage.reference.child(path)
//        listRef.listAll()
//            .addOnSuccessListener { listResult ->
//                frameList.postValue(listResult.items)
//                println("RAJRONNAK  ${listResult.items.toString()}")
//            }
//            .addOnFailureListener {  }
//    }
//
//    val storage2: FirebaseStorage = FirebaseStorage.getInstance()
//    fun fetchAllSticker(frameList : MutableLiveData<List<StorageReference>>,path: String){
//        val listRef = storage2.reference.child(path)
//        listRef.listAll()
//            .addOnSuccessListener { listResult ->
//                frameList.postValue(listResult.items)
//                println("BIHAR  ${listResult.items}")
//            }
//            .addOnFailureListener {  }
//    }


}







