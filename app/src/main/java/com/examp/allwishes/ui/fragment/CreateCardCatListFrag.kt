package com.examp.allwishes.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.examp.allwishes.R
import com.examp.allwishes.databinding.AddbgDiloglayoutBinding
import com.examp.allwishes.databinding.GradentpicklayoutBinding
import com.examp.allwishes.ui.activity.MainActivity
import com.examp.allwishes.ui.adapter.AddbgCardAdapter
import com.examp.allwishes.ui.adapter.ChooseColorAdapter
import com.examp.allwishes.ui.adapter.ChooseGradCloreAdapter
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.util.OnItemClickListener
import com.examp.allwishes.ui.viewmodel.CreateCardViewModel
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.skydoves.colorpickerview.AlphaTileView
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.flag.BubbleFlag
import com.skydoves.colorpickerview.flag.FlagMode
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.sliders.AlphaSlideBar
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar
import timber.log.Timber


class CreateCardCatListFrag : Fragment() {
    private lateinit var b: AddbgDiloglayoutBinding
    private var addbgcolordialog: Dialog? = null
    private lateinit var createCardViewModel: CreateCardViewModel
    private lateinit var mainViewModel: HomeViewModel

    private var colorpickdialog: Dialog? = null
    var aalphaTileview: AlphaTileView? = null
    private var name: String = ""
    private var cat_addrs: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = AddbgDiloglayoutBinding.inflate(inflater, container, false)

        name = arguments?.getString("catName").toString()
        cat_addrs = "CreateCards/" + name
        createCardViewModel = ViewModelProvider(this)[CreateCardViewModel::class.java]
        mainViewModel =
            ViewModelProvider(
                requireActivity(),
                MyViewModelFactory(FirebaseHelper())
            )[HomeViewModel::class.java]
        mainViewModel.loadImagesStorage(name)
        createCardViewModel.colorList()
        createCardViewModel.fontList()
        createCardViewModel.gradientList()
        b.colorid.setOnClickListener {
            Toast.makeText(requireContext() , "clicked on ColorID" , Toast.LENGTH_SHORT).show()
            colorDilog()
        }
        b.galleryid.setOnClickListener {
//            b.createimageview.setOnTouchListener(MultiTouchListener())
            AppUtils.getpicGallery(requireActivity())
        }

//        b.catname.setText(name)

        mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity())
        { resource ->
            createAdapter(resource)
        }

        return b.root
    }

    private fun createAdapter(resource: List<StorageReference>) {
        if (resource.size > 0) {
            b.bgprogressbarid.progresbarid.visibility = View.GONE
        }

        val adapter = AddbgCardAdapter(
            requireActivity(), resource, object : OnItemClickListener {
                override fun onClick(position: Int) {
                    val b = Bundle()
                    AppUtils.changeFragmentWithPosition(
                        findNavController(),
                        R.id.action_nav_create_cards_list_to_nav_set_cards,
                        requireActivity(),
                        b
                    )
                }
            })
        b.addbgrecycleid.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun colorDilog() {


        val colordilogslayout = layoutInflater.inflate(R.layout.addbg_colorlayout, null)
        addbgcolordialog = Dialog(requireContext(), R.style.addbgWideDialog)

        addbgcolordialog?.setContentView(colordilogslayout)
        addbgcolordialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme2;

        addbgcolordialog?.setCancelable(true)
        addbgcolordialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addbgcolordialog?.window?.setGravity(Gravity.BOTTOM)


        var dilogrecycler1: RecyclerView = colordilogslayout.findViewById(R.id.onecolorrecyView)
        val coloranim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_ups)
        dilogrecycler1.startAnimation(coloranim)
        createCardViewModel.colorlivedata.observe(
            requireActivity(),
            androidx.lifecycle.Observer { bgcolorlist ->

                val colorAdapter = ChooseColorAdapter(
                    bgcolorlist,
                    requireContext(),
                    object : OnItemClickListener {
                        override fun onClick(position: Int) {
                            if (position == 0) {
                                pickDilog()
                                addbgcolordialog?.dismiss()
                            } else {
//                                binding.createimageview.setImageDrawable(null)
//                                binding.createimageview.setBackgroundColor(bgcolorlist[position].color)
                                addbgcolordialog?.dismiss()
                            }
                        }
                    })
                dilogrecycler1.adapter = colorAdapter

            })

        val dilogrecycler3: RecyclerView = colordilogslayout.findViewById(R.id.gridentrecycView)



        dilogrecycler3.startAnimation(coloranim)
        //----


        dilogrecycler3.apply {
            createCardViewModel.gradientlivedata.observe(
                requireActivity(),
                androidx.lifecycle.Observer { gradinentdatalist ->
                    val chooseGradCloreAdapter = ChooseGradCloreAdapter(
                        gradinentdatalist,
                        requireContext(),
                        object : OnItemClickListener {
                            override fun onClick(position: Int) {
//                                binding.createimageview.setImageDrawable(null)

                                if (position == 0) {
                                    gradentpickDilog()
//                                    gradentpickDilog2()
                                    addbgcolordialog?.dismiss()

                                } else {

                                    val gd = GradientDrawable(
                                        GradientDrawable.Orientation.TOP_BOTTOM,
                                        gradinentdatalist[position].gradlist
                                    )
                                    gd.cornerRadius = 0f

//                                    binding.createimageview.setBackgroundDrawable(gd)
                                    addbgcolordialog?.dismiss()

                                }


                            }
                        })

                    adapter = chooseGradCloreAdapter
                })
        }

        addbgcolordialog?.show()

    }


    fun pickDilog() {
        val colorpiclayout = layoutInflater.inflate(R.layout.colorpicklayout, null)
        colorpickdialog = Dialog(requireContext(), R.style.WideDialogcolorpic)
        colorpickdialog?.setContentView(colorpiclayout)
        colorpickdialog?.setCancelable(true)
        colorpickdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        colorpickdialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)


        val cancelbtn: ImageView = colorpiclayout.findViewById(R.id.cancelbtnid)
        val colorpickview: ColorPickerView = colorpiclayout.findViewById(R.id.colorPickerView)
        val alphaSlidebar: AlphaSlideBar = colorpiclayout.findViewById(R.id.alphaSlideBar)
        val brightnessslide: BrightnessSlideBar = colorpiclayout.findViewById(R.id.brightnessSlide)
        aalphaTileview = colorpiclayout.findViewById(R.id.alphatileView)
        val done_btn: TextView = colorpiclayout.findViewById(R.id.okid)
        val percenTage: TextView = colorpiclayout.findViewById(R.id.percentage)
        var seekbarid: SeekBar = colorpiclayout.findViewById(R.id.seekbar)





        cancelbtn.setOnClickListener {
            colorpickdialog?.dismiss()
        }

        val bubbleFlag = BubbleFlag(requireContext())
        bubbleFlag.flagMode = FlagMode.ALWAYS
        var envelope2: ColorEnvelope? = null
        colorpickview.flagView = bubbleFlag
        colorpickview.setColorListener(
            ColorEnvelopeListener { envelope: ColorEnvelope, fromUser: Boolean ->
                envelope2 = envelope

                aalphaTileview?.setPaintColor(envelope.color)
                Timber.d("color: %s", envelope.hexCode)
//                        percenTage.text = setColorAlpha(100,"#" + envelope.hexCode)


            })

        colorpickview.attachAlphaSlider(alphaSlidebar)
        colorpickview.attachBrightnessSlider(brightnessslide)
        colorpickview.setLifecycleOwner(this)





        done_btn.setOnClickListener {
//            binding.createimageview.setImageResource(0)
//            binding.cardrootlayout.setBackgroundResource(0)
//            binding.create.visibility = View.GONE
//            binding.createimageview.setBackgroundColor(envelope2!!.color)
            colorpickdialog?.dismiss()
        }



        seekbarid.visibility = View.GONE
        seekbarid.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {


                percenTage.setText("" + progress + "%")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        colorpickdialog?.show()
    }


    fun gradentpickDilog() {


        var gradbinding = GradentpicklayoutBinding.inflate(layoutInflater)
        var gradDialog = Dialog(requireContext(), R.style.WideDialogcolorpic)
        gradDialog?.setContentView(gradbinding.root)
        gradDialog?.setCancelable(true)
        gradDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        gradDialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)

        gradbinding.cancelbtnid.setOnClickListener {
            gradDialog?.dismiss()
        }


//        val bubbleFlag = BubbleFlag(this)
//        bubbleFlag.flagMode = FlagMode.ALWAYS
//        var envelopeGr1: ColorEnvelope? = null
//        gradbinding.colorPickerViewgr1.flagView = bubbleFlag
//
//        gradbinding.colorPickerViewgr1.setColorListener(
//            ColorEnvelopeListener { envelope: ColorEnvelope, fromUser: Boolean ->
//                envelopeGr1 = envelope
//
//                Timber.d("color: %s", envelope.hexCode)
//                gradbinding.alphatileView?.setPaintColor(envelope.color)
//
//            })
//
//        gradbinding.colorPickerViewgr1.attachAlphaSlider(gradbinding.graalphaSlideBar)
//        gradbinding.colorPickerViewgr1.setLifecycleOwner(this)


//--2
//        val bubbleFlag2 = BubbleFlag(this)
//        bubbleFlag2.flagMode = FlagMode.ALWAYS
//        var envelopeGr2: ColorEnvelope? = null
//        gradbinding.colorPickerViewgr2.flagView = bubbleFlag2
//        gradbinding.colorPickerViewgr2.setColorListener(
//            ColorEnvelopeListener {envelope2: ColorEnvelope, fromUser: Boolean ->
//                envelopeGr2 = envelope2
//
//                Timber.d("color: %s", envelope2.hexCode)
//                gradbinding.alphatileView?.setPaintColor(envelope2.color)
//
//            })
//        gradbinding.colorPickerViewgr2.attachAlphaSlider(gradbinding.graalphaSlideBar)
//        gradbinding.colorPickerViewgr2.setLifecycleOwner(this)


        val pk = BubbleFlag(requireContext())
        pk.flagMode = FlagMode.ALWAYS

        var env: ColorEnvelope? = null
        var env2: ColorEnvelope? = null

//        gradbinding.colorPickerViewgr2.flagView = pk
        gradbinding.colorPickerViewgr1.flagView = pk

        gradbinding.colorPickerViewgr1.setColorListener(
            ColorEnvelopeListener { envelopet: ColorEnvelope, fromUser: Boolean ->
                env = envelopet

//                Timber.d("color: %s", envelope2.hexCode)
                gradbinding.alphatileView?.setPaintColor(envelopet.color)

            })

        gradbinding.colorPickerViewgr2.setColorListener(
            ColorEnvelopeListener { envelopet: ColorEnvelope, fromUser: Boolean ->
                env2 = envelopet
                println("RALKLLL " + env2)

//                Timber.d("color: %s", envelope2.hexCode)
                gradbinding.alphatileView?.setPaintColor(envelopet.color)

            })

        gradbinding.colorPickerViewgr1.attachAlphaSlider(gradbinding.graalphaSlideBar)
        gradbinding.colorPickerViewgr1.setLifecycleOwner(this)
        gradbinding.colorPickerViewgr2.attachAlphaSlider(gradbinding.graalphaSlideBar)
        gradbinding.colorPickerViewgr2.setLifecycleOwner(this)


        gradbinding.gokid.setOnClickListener {
//            binding.createimageview.setImageResource(0)
//            binding.cardrootlayout.setBackgroundResource(0)
//            binding.create.visibility = View.GONE
//            binding.createimageview.setBackgroundColor(env!!.color)
            gradDialog?.dismiss()
        }

        gradDialog?.show()
    }

}