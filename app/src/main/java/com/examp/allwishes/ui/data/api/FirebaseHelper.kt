package com.examp.allwishes.ui.data.api

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseHelper() {

    suspend fun loadRealTimeData(): List<com.examp.allwishes.ui.model.Root_Hl> {
        return suspendCoroutine<List<com.examp.allwishes.ui.model.Root_Hl>> {
            val database: DatabaseReference = Firebase.database.reference
            val modelList = ArrayList<com.examp.allwishes.ui.model.Root_Hl>()
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //val dataSnapshot = database.get().await()
                    for (child in dataSnapshot.children) {
                        val mainModel = child.getValue(com.examp.allwishes.ui.model.Root_Hl::class.java)!!
                        modelList.add(mainModel)
                    }
                    it.resume(modelList)
                }

                override fun onCancelled(error: DatabaseError) {
                    it.resume(modelList)
                }
            })
        }
    }

    suspend fun loadImagesStorage(catName: String): List<StorageReference>? {
        return suspendCoroutine<List<StorageReference>> {
            print("Anshu loadImagesStorage")
            val storage = Firebase.storage
//            var listNames: List<StorageReference>? = null
            val listRef = storage.reference.child("$catName")
            val r = listRef.listAll()
            r.addOnSuccessListener { listResult ->
                print("Anshu addOnSuccessListener")
                it.resume(listResult.items)
            }
            r.addOnCanceledListener {
                print("Anshu addOnCanceledListener")
            }
            r.addOnFailureListener {
                print("Anshu addOnFailureListener " + it.message)
            }
        }
    }

}