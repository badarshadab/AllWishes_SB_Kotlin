package com.greetings.allwishes.ui.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.model.ColorModel
import com.greetings.allwishes.data.model.createCardMode.FontsModel
import com.greetings.allwishes.data.model.createCardMode.GradientModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.greetings.allwishes.Repo
import org.json.JSONArray
import java.io.File

class CreateCardViewModel : ViewModel() {

    //Singlecolor
    var colorlist: ArrayList<ColorModel> = ArrayList()
    val colormutdata = MutableLiveData<ArrayList<ColorModel>>()
    val colorlivedata: LiveData<ArrayList<ColorModel>> = colormutdata

    // fontFamily
    var fontlists: ArrayList<FontsModel> = ArrayList()
    val fontmutdata = MutableLiveData<ArrayList<FontsModel>>()
    val fontlivedata: LiveData<ArrayList<FontsModel>> = fontmutdata

    //GradientColor
    var gradientlist: ArrayList<GradientModel> = ArrayList()
    val gradientmutdata = MutableLiveData<ArrayList<GradientModel>>()
    val gradientlivedata: LiveData<ArrayList<GradientModel>> = gradientmutdata

    fun colorList() {
        colorlist.add(ColorModel(Color.parseColor("#FFBE18")))
        colorlist.add(ColorModel(Color.parseColor("#138EFF")))
        colorlist.add(ColorModel(Color.parseColor("#C41BFF")))
        colorlist.add(ColorModel(Color.parseColor("#05B837")))
        colorlist.add(ColorModel(Color.parseColor("#04ECC2")))
        colorlist.add(ColorModel(Color.parseColor("#FF003D")))
        colorlist.add(ColorModel(Color.parseColor("#FF9874")))
        colorlist.add(ColorModel(Color.parseColor("#FF00C7")))
        colorlist.add(ColorModel(Color.parseColor("#935F11")))
        colorlist.add(ColorModel(Color.parseColor("#00FF19")))
        colorlist.add(ColorModel(Color.parseColor("#5BC4FF")))
        colorlist.add(ColorModel(Color.parseColor("#EEFF41")))
        colorlist.add(ColorModel(Color.parseColor("#E65100")))
        colorlist.add(ColorModel(Color.parseColor("#B0BEC5")))
        colorlist.add(ColorModel(Color.parseColor("#000000")))
        colorlist.add(ColorModel(Color.parseColor("#29B37A")))
        colorlist.add(ColorModel(Color.parseColor("#2968B3")))
        colorlist.add(ColorModel(Color.parseColor("#B37429")))
        colorlist.add(ColorModel(Color.parseColor("#F52923")))
        colorlist.add(ColorModel(Color.parseColor("#F3D80E")))
        colorlist.add(ColorModel(Color.parseColor("#920EF3")))
        colorlist.add(ColorModel(Color.parseColor("#E998F0")))
        colorlist.add(ColorModel(Color.parseColor("#AD98F0")))
        colormutdata.postValue(colorlist)

    }

    fun fontList() {
        fontlists.add(FontsModel(R.font.font1))
        fontlists.add(FontsModel(R.font.font2))
        fontlists.add(FontsModel(R.font.font3))
        fontlists.add(FontsModel(R.font.font4))
        fontlists.add(FontsModel(R.font.font5))
        fontlists.add(FontsModel(R.font.font6))
        fontlists.add(FontsModel(R.font.font7))
        fontlists.add(FontsModel(R.font.font8))
        fontlists.add(FontsModel(R.font.montserrat))
        fontlists.add(FontsModel(R.font.font10))
        fontlists.add(FontsModel(R.font.font11))
        fontlists.add(FontsModel(R.font.font11b))
        fontlists.add(FontsModel(R.font.font12))
        fontlists.add(FontsModel(R.font.font13))
        fontlists.add(FontsModel(R.font.font14))
        fontlists.add(FontsModel(R.font.font15))
        fontlists.add(FontsModel(R.font.font17))
        fontlists.add(FontsModel(R.font.montserrat))
        fontmutdata.postValue(fontlists)

    }

    fun gradientList() {
        gradientlist.add(GradientModel(intArrayOf(0xff255779.toInt(), 0xffa6c0cd.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xffffc425.toInt(), 0xfff82aff.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff16fffb.toInt(), 0xffff1cc5.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xffafff50.toInt(), 0xffffe04f.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xffeecda3.toInt(), 0xffef629f.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xfffffe5f.toInt(), 0xffa4c6ff.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff606eff.toInt(), 0xffff2983.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff4622ff.toInt(), 0xffea36ff.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff6dd5ed.toInt(), 0xff2193b0.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff56ab2f.toInt(), 0xffa8e063.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xffffffff.toInt(), 0xff6dd5ed.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xfffc4a1a.toInt(), 0xfff7b733.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff16fffb.toInt(), 0xffff1cc5.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff1fddff.toInt(), 0xffff4b1f.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xffa80077.toInt(), 0xff66ff00.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff00d2ff.toInt(), 0xff3a7bd5.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xfff2709c.toInt(), 0xffff9472.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xff00416A.toInt(), 0xff00416A.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xffAAFFA9.toInt(), 0xff11FFBD.toInt())))
        gradientlist.add(GradientModel(intArrayOf(0xffFF4E50.toInt(), 0xffF9D423.toInt())))
        gradientmutdata.postValue(gradientlist)
    }




//  Backgroundimg

    private var mutdata: MutableLiveData<List<StorageReference>>? = null
    var categoryPath: String = ""

    fun getALLGif(path: String): MutableLiveData<List<StorageReference>>? {
        if (categoryPath != path) {
            mutdata = MutableLiveData()
            loadCategorybackground(path)
        }
        return mutdata
    }

    private fun loadCategorybackground(path: String) {
//        categoryPath = path
//        mutdata?.let { Repo.fetchAllSticker(it, "$path/backgroundimg") }
    }


    //-- sticker
    private var stickermutdata: MutableLiveData<List<StorageReference>>? = null
    var categoryPath2: String = ""

    fun getALLSticker(stpath: String): MutableLiveData<List<StorageReference>> {
        if (categoryPath2 != stpath) {
            stickermutdata = MutableLiveData()
            loadCategorysticker(stpath)
        }
        return stickermutdata as MutableLiveData<List<StorageReference>>
    }

    private fun loadCategorysticker(stpath: String) {
//        categoryPath = stpath
//        stickermutdata?.let { Repo.fetchAllFrames(it, "$stpath/sticker") }
    }

    //-- quotes

    private var quotes = MutableLiveData<List<String>>()
    fun getData(catName: String): LiveData<List<String>> {
        getQuotes(catName)
        return quotes
    }


    private fun getQuotes(catName: String) {
        val storage = Firebase.storage
        val listRef = storage.reference.child(catName + "/quotes")
        listRef.listAll()
            .addOnSuccessListener { listResult ->
                val localFile = File.createTempFile("quotes", "txt")
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
                quotes.value = ArrayList()
            }


    }
}