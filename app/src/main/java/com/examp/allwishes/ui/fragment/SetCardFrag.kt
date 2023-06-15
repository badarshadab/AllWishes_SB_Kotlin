package com.examp.allwishes.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examp.allwishes.R
import com.examp.allwishes.databinding.ActivityCreateBinding
import com.examp.allwishes.databinding.AddbgDiloglayoutBinding
import com.examp.allwishes.databinding.AddtextDialogLayBinding
import com.examp.allwishes.databinding.GradentpicklayoutBinding
import com.examp.allwishes.databinding.QuoteslistdlayoutBinding
import com.examp.allwishes.databinding.StickerdiloglayoutBinding
import com.examp.allwishes.ui.adapter.AddbgCardAdapter
import com.examp.allwishes.ui.adapter.ChooseColorAdapter
import com.examp.allwishes.ui.adapter.ChooseGradCloreAdapter
import com.examp.allwishes.ui.adapter.FontsAdapter
import com.examp.allwishes.ui.adapter.QuotesAdapter
import com.examp.allwishes.ui.adapter.StickerAdpter
import com.examp.allwishes.ui.adapter.TextChooseColorAdapter
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.model.ColorModel
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.util.BubbleTextView
import com.examp.allwishes.ui.util.MultiTouchListener
import com.examp.allwishes.ui.util.OnItemClickListener
import com.examp.allwishes.ui.util.OnItemClickListener_Quotes
import com.examp.allwishes.ui.util.StickerImageView
import com.examp.allwishes.ui.util.StickerOnItemClick
import com.examp.allwishes.ui.viewmodel.CreateCardViewModel
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.examp.allwishes.ui.viewmodel.QuoteViewModel
import com.google.android.material.card.MaterialCardView
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
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream


class SetCardFrag : Fragment(), View.OnClickListener {

    companion object {
        lateinit var mainContainer: MaterialCardView
    }

    lateinit var fontdata: Typeface
    var texteditcolor: Int = 0

    private lateinit var b: ActivityCreateBinding
    private var addbgcolordialog: Dialog? = null
    private lateinit var createCardViewModel: CreateCardViewModel
    private lateinit var quoteViewModel: QuoteViewModel
    private lateinit var mainViewModel: HomeViewModel
    private var addbgbinding: AddbgDiloglayoutBinding? = null
    private var addtextbinding: AddtextDialogLayBinding? = null
    private var addbgdialog: Dialog? = null
    lateinit var quotesbinding: QuoteslistdlayoutBinding
    private var quotelistdialog: Dialog? = null
    lateinit var stickerBinding: StickerdiloglayoutBinding
    private var stickerdialog: Dialog? = null
    lateinit var stickerImageView: StickerImageView
    var msgToAdd: String = ""


    private var colorpickdialog: Dialog? = null
    var aalphaTileview: AlphaTileView? = null
    private var name: String = ""
    private var cat_addrs: String = ""
    private val GALLERY: Int = 1
    private val CAMERA: Int = 2
    var dtextView: TextView? = null
    var fontlist: String = ""
    var quotetext: TextView? = null
    private var addtextdialog: Dialog? = null
    lateinit var imageUri: Uri

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        b.createimageview.setImageURI(null)
        b.createimageview.setImageURI(imageUri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Create Cards"
        super.onViewCreated(view, savedInstanceState)
    }

    lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
//        https://stackoverflow.com/questions/28672883/java-lang-illegalstateexception-fragment-not-attached-to-activity
//        Fragment ContentPreviewFragment{fb22d83} (743b8906-1fa7-4828-8024-cc60ff8aac63) not attached to an activity.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = ActivityCreateBinding.inflate(inflater, container, false)

        name = arguments?.getString("catName").toString()
        cat_addrs = "CreateCards/" + name
        createCardViewModel = ViewModelProvider(this)[CreateCardViewModel::class.java]
        setupViewModel()
        mainViewModel.loadImagesStorage(cat_addrs + "/cards")
        quotetext = TextView(requireContext())
        quotetext?.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mainContainer = b.cardsharesaveid
        imageUri = AppUtils.createImageUri(requireContext())!!
        quotetext?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5f)
        quotetext?.gravity = (Gravity.BOTTOM)
//        addbgDilog()
        createCardViewModel.colorList()
        createCardViewModel.fontList()
        createCardViewModel.gradientList()
        b.addbgid.setOnClickListener(this)
        b.addtextid.setOnClickListener(this)
        b.quoteid.setOnClickListener(this)
        b.fontsid.setOnClickListener(this)
        b.textcolorid.setOnClickListener(this)
        b.stickersid.setOnClickListener(this)
        b.nextBtn.setOnClickListener {
            nextBtnClick()
        }
        b.create.setOnClickListener(this)

//        stickerImageView.setOnClickListener{
////            stickerImageView
//            Toast.makeText(requireContext(), "clicked on stickerImageView", Toast.LENGTH_SHORT).show()
//        }

        return b.root
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]
    }

    private fun addbgDilog() {

        addbgbinding = AddbgDiloglayoutBinding.inflate(layoutInflater)
        addbgdialog = Dialog(requireContext(), R.style.addbgWideDialog)
        addbgdialog?.setContentView(addbgbinding!!.root)
        addbgdialog?.setCancelable(true)
        addbgdialog!!.window?.attributes?.windowAnimations = R.style.DialogTheme4
        addbgdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addbgdialog?.window?.setGravity(Gravity.BOTTOM)

        addbgbinding!!.colorid.setOnClickListener {

//            Toast.makeText(requireContext(), "clicked on ColorID", Toast.LENGTH_SHORT).show()
            colorDilog()
            if (b.createimageview != null) {
                b.create.visibility = View.GONE
            }
            addbgdialog?.dismiss()
        }

        addbgbinding!!.galleryid.setOnClickListener {
//            b.create.visibility = View.GONE
            b.createimageview.setOnTouchListener(MultiTouchListener())
            AppUtils.getpicGallery(requireActivity())
            if (b.createimageview != null) {
                b.create.visibility = View.GONE
            }
            addbgdialog?.dismiss()
        }

        addbgbinding!!.cameraid.setOnClickListener {

            val mListener = MultiTouchListener()
            mListener.minimumScale = 0.1f
            b.createimageview.setOnTouchListener(mListener)
            AppUtils.checkCameraPermission(requireContext()) { it ->
                if (it) {
                    contract.launch(imageUri)
                }
            }

//            if (AppUtils.checkPermissionFor33(requireContext(), activity)) {
//                contract.launch(imageUri)
//            } else {
//                AppUtils.requestPermission(activity)
//            }
            if (b.createimageview != null) {
                b.create.visibility = View.GONE
            }


            addbgdialog?.dismiss()

        }
        addbgbinding!!.chooseBg.setText(name)
//        addbgbinding!!.catname.setText(name)

        mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity())
        { resource ->
            createAdapter(resource)
        }
        addbgdialog?.show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            AppUtils.RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    AppUtils.requestPermission(activity)
                } else {
                    contract.launch(imageUri)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) {
            b.createimageview.setImageURI(data?.data)
        } else if (requestCode == CAMERA) {
            val photo = data!!.extras!!["data"] as Bitmap?
            // Set the image in imageview for display
            b.createimageview.setImageBitmap(photo)
        }
    }

    private fun createAdapter(resource: List<StorageReference>) {
        if (resource.size > 0) {
            addbgbinding!!.bgprogressbarid.progresbarid.visibility = View.GONE
        }
        if (isAdded) {
            val activity = requireActivity()
            activity?.let {
                val adapter = AddbgCardAdapter(
                    activity = activity, resource, object : OnItemClickListener {
                        override fun onClick(position: Int) {

                            Glide.with(requireContext()).load(resource[position])
                                .centerCrop()
                                .placeholder(R.drawable.loading_img)
                                .into(b.createimageview)
                            addbgdialog?.dismiss()
                            val mListener = MultiTouchListener()
                            mListener.minimumScale = 0.1f
                            if (b.createimageview != null) {
                                b.create.visibility = View.GONE
                            }
                            b.createimageview.setOnTouchListener(mListener)
                        }
                    })
                addbgbinding!!.addbgrecycleid.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
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
                                b.createimageview.setImageDrawable(null)
                                b.createimageview.setBackgroundColor(bgcolorlist[position].color)
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
                                b.createimageview.setImageDrawable(null)

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

                                    b.createimageview.setBackgroundDrawable(gd)
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
//            b.createimageview.setImageResource(0)
//            b.cardrootlayout.setBackgroundResource(0)
//            b.create.visibility = View.GONE
//            b.createimageview.setBackgroundColor(envelope2!!.color)
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
//            b.createimageview.setImageResource(0)
//            b.cardrootlayout.setBackgroundResource(0)
//            b.create.visibility = View.GONE
//            b.createimageview.setBackgroundColor(env!!.color)
            gradDialog?.dismiss()
        }

        gradDialog?.show()
    }

    fun addTextDilog() {
        addtextbinding = AddtextDialogLayBinding.inflate(layoutInflater)
        addtextdialog = Dialog(requireContext(), R.style.WideDialog)
        addtextdialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme2

        addtextdialog?.setContentView(addtextbinding!!.root)
        addtextdialog?.setCancelable(true)
        addtextdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        fontdata = Typeface.createFromAsset(requireContext().assets, "1.ttf")
        texteditcolor = Color.parseColor("#FF000000")

        addtextbinding?.cancelBtn?.setOnClickListener {
            addtextdialog?.dismiss()
        }
        addtextbinding?.addokbtn?.setOnClickListener {

            msgToAdd = addtextbinding!!.textid.text.toString()
            dtextView?.text = msgToAdd
            if (!msgToAdd.isEmpty()) {
                b.addtextid.setText(R.string.remove_text)
                b.create.visibility = View.GONE
//                dtextView!!.setOnClickListener(this)
            }
            val triple = Triple(msgToAdd, texteditcolor, fontdata);
            addStringToView(requireContext(), triple)
            addtextdialog?.dismiss()
        }


        addtextdialog?.show()
    }


    override fun onClick(v: View?) {
        when (v) {
            b.addbgid -> {
                addBgClick()
            }

            b.addtextid -> {

                addOrRemove(b.addtextid, getString(R.string.add_text),
                    { addTextClick() })

            }

            b.quoteid -> {
                quotesIDClick()
            }

            b.fontsid -> {
                fontsIDClick()
            }

            b.textcolorid -> {
                textColorClick()
            }

            b.stickersid -> {
                b.toolbartitle.setText(R.string.stickers)
                addOrRemove(b.stickersid, getString(R.string.stickers),
                    { stickersDilog() })
            }

            b.create -> {
                addBgClick()
            }

            dtextView -> {
                Toast.makeText(requireContext(), "clicked on dtextView", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addBgClick() {

        b.fontcolorlistlayout.visibility = View.GONE
        addbgDilog()
        b.nextBtn.visibility = View.VISIBLE

    }

    fun addTextClick() {
        b.fontcolorlistlayout.visibility = View.GONE

        addTextDilog()

        dtextView = TextView(requireContext())

        dtextView?.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dtextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)

        dtextView?.gravity = (Gravity.CENTER)

        val typeface: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.font11b)
        dtextView?.setTypeface(typeface)
        dtextView?.textSize = 40f

    }

    fun quotesIDClick() {
        b.create.visibility = View.GONE
        b.toolbartitle.setText(R.string.text_Quotes)

        quotesListDilog()
        b.nextBtn.visibility = View.VISIBLE
    }

    fun fontsIDClick() {
        b.toolbartitle.setText(R.string.fonts)
        b.fontcolorlistlayout.visibility = View.VISIBLE
        b.fontcolorlistlayout.setBackgroundColor(Color.WHITE)

        fontlist = "fontABCD"
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_ups)
        b.fontcolorlistlayout.startAnimation(anim)
        b.listfontcolorrecyclerview.apply {
            createCardViewModel.fontlivedata.observe(
                requireActivity(),
                androidx.lifecycle.Observer { fontdatalist ->
                    val fontsAdapter = FontsAdapter(
                        fontdatalist,
                        requireActivity(),
                        object : OnItemClickListener {
                            override fun onClick(position: Int) {
                                val fontstyle = ResourcesCompat.getFont(
                                    context,
                                    fontdatalist[position].fontstyle!!
                                )
                                dtextView?.typeface = fontstyle
                                quotetext?.typeface = fontstyle
                            }
                        })
                    fontdatalist.shuffle()
                    adapter = fontsAdapter

                })

        }
    }

    fun textColorClick() {
        b.toolbartitle.setText(R.string.text_color)
        b.fontcolorlistlayout.visibility = View.VISIBLE
        b.fontcolorlistlayout.setBackgroundColor(Color.WHITE)

//            b.fontcolorlistlayout.visibility = View.GONE
        val anim2: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left)
        b.fontcolorlistlayout.startAnimation(anim2)

        createCardViewModel.colorlivedata.observe(
            requireActivity(),
            androidx.lifecycle.Observer { fonttextcolor ->

            })


        createCardViewModel.colorlivedata.observe(
            requireActivity(),
            androidx.lifecycle.Observer { fonttextcolor ->
                setFontColorAdapter(fonttextcolor)

            })


        b.nextBtn.visibility = View.VISIBLE
    }

    fun setFontColorAdapter(fonttextcolor: ArrayList<ColorModel>) {
        val textAdapter = TextChooseColorAdapter(
            fonttextcolor,
            requireContext(),
            object : OnItemClickListener {
                override fun onClick(position: Int) {
                    if (position == 0) {
                        textPickDilog()
                    } else {
                        dtextView?.setTextColor(fonttextcolor[position].color)
                        quotetext?.setTextColor(fonttextcolor[position].color)
                    }
                }
            })
        b.listfontcolorrecyclerview.adapter = textAdapter
    }

    fun quotesListDilog() {

        quotesbinding = QuoteslistdlayoutBinding.inflate(layoutInflater)
        quotelistdialog = Dialog(requireContext(), R.style.WideDialog)
        quotelistdialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        quotelistdialog?.setContentView(quotesbinding.root)
        val lout = WindowManager.LayoutParams()
        lout.copyFrom(quotelistdialog?.getWindow()?.getAttributes())
        lout.width = WindowManager.LayoutParams.MATCH_PARENT
        lout.height = WindowManager.LayoutParams.MATCH_PARENT
        quotelistdialog?.setCancelable(true)
        quotelistdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        quotelistdialog?.getWindow()?.setAttributes(lout)

//        SmAdds.showNativeBannerAdd(requireActivity(), quotesbinding.bottomaddsccard)

        quotesbinding.quotesbackid.setOnClickListener {
            quotelistdialog?.dismiss()
        }


        quoteViewModel.getQuotes(cat_addrs + "/quotes")
        quoteViewModel.quotes.observe(requireActivity()) { list ->
            setQuotesAdapter(list)
        }


        quotelistdialog?.show()
    }


    fun textPickDilog() {
        val colorpiclayout = layoutInflater.inflate(R.layout.colorpicklayout, null)
        colorpickdialog = Dialog(requireContext(), R.style.WideDialog)
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

            b.create.visibility = View.GONE
            dtextView?.setTextColor(envelope2!!.color)
            quotetext?.setTextColor(envelope2!!.color)

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

    fun stickersDilog() {
        stickerBinding = StickerdiloglayoutBinding.inflate(layoutInflater)
        stickerdialog = Dialog(requireContext(), R.style.addbgWideDialog)
        stickerdialog?.setContentView(stickerBinding.root)
        stickerdialog?.setCancelable(true)

        stickerdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        stickerdialog?.window?.setGravity(Gravity.BOTTOM)
        stickerdialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme4;


//        SmAdds.showBannerAdd(this@CreateCardActivity, stickerBinding.bottomaddscstickerid)

        mainViewModel.loadImagesStorage(cat_addrs + "/stickers")
        stickerBinding.stickerrecycleid.apply {

            mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity())
            { resource ->
                setStickersAdapter(resource)
            }

        }


        stickerdialog?.show()
    }

    fun setStickersAdapter(resource: List<StorageReference>) {
        val stickerAdpter =
            StickerAdpter(
                resource,
                activity,
                object : StickerOnItemClick {
                    override fun onClick(view: View, position: Int) {
                        stickerImageView = StickerImageView(requireContext())
                        println("onTouch stickerImageView" + stickerImageView.getTag())
                        b.stickersid.text = getString(R.string.remove)

//                        stickerImageView.setImageResource(R.drawable.ic_stickers)
                        stickerImageView.imageBitmap = view?.drawToBitmap()
                        b.cardrootlayout.addView(stickerImageView)
                        stickerdialog?.dismiss()
                    }
                })

        stickerBinding.stickerrecycleid.adapter = stickerAdpter
    }

    fun setQuotesAdapter(list: List<String>) {
        var quotesAdapter = QuotesAdapter(
            list,
            requireContext(),
            object : OnItemClickListener_Quotes {
                override fun onClick(position: Int) {

                    quotetext?.text = list[position]
                    val typeface: Typeface? =
                        ResourcesCompat.getFont(requireContext(), R.font.font11b)
                    quotetext?.setTypeface(typeface)
                    quotetext?.textSize = 30f

                    quotetext?.setOnTouchListener(MultiTouchListener())

                    if (quotetext?.getParent() != null) {
                        (quotetext?.getParent() as ViewGroup).removeView(quotetext)
                    }
                    b.cardrootlayout.addView(quotetext)
                    quotelistdialog?.dismiss()

                }
            })
        quotesbinding.recyclerquote.adapter = quotesAdapter
        quotesbinding.quoteprocessid.progresbarid.visibility = View.GONE
    }

    fun saveBitmap(bitmap: Bitmap): String? {
        var fileName: String? = "ImageName" //no .png or .jpg needed
        try {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val fo: FileOutputStream =
                requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)
            fo.write(bytes.toByteArray())
            // remember close file output
            fo.close()
        } catch (e: Exception) {
            e.printStackTrace()
            fileName = null
        }
        return fileName
    }

    fun nextBtnClick() {
        if (b.createimageview.drawable != null || (dtextView != null && !msgToAdd.isEmpty())) {
            var viewdata = AppUtils.getBitmapFromView(b.cardsharesaveid)
            val bun = Bundle()
            bun.putString("bitimgkey", saveBitmap(viewdata))
            AppUtils.changeFragment(requireActivity(), R.id.nav_card_preview, bun)
        } else {
            Toast.makeText(requireContext(), "Set Image or Text First", Toast.LENGTH_SHORT).show()
        }
    }

    fun addOrRemove(v: TextView, addText: String, lmbd: () -> Unit) {
        if (v.getText().equals(addText)) {
            lmbd()
        } else {
            v.text = addText
            if (v == b.stickersid && stickerImageView != null) {
                stickerImageView.setImageDrawable(null)
            } else if (v == b.addtextid && dtextView != null) {
                dtextView?.text = ""
            }
        }
    }

    private fun addStringToView(context: Context, triple: Triple<String, Int, Typeface>) {
        val string = triple.first
        val tv_sticker = BubbleTextView(context, triple.second, triple.third, 0, string)
        removeAddedView(tv_sticker)
        tv_sticker.setOperationListener(object : BubbleTextView.OperationListener {
            override fun onDeleteClick() {
                removeAddedView(tv_sticker)
            }

            override fun onEdit(bubbleTextView: BubbleTextView?) {
                bubbleTextView.let {
                    val onEdit = !bubbleTextView?.isInEditMode!!
                    tv_sticker.setInEdit(onEdit)
                }
            }

            override fun onClick(bubbleTextView: BubbleTextView?) {
            }

            override fun onTop(bubbleTextView: BubbleTextView?) {
            }
        })

        if (string.length <= 200) {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb_250)
        } else if (string.length > 200 && string.length < 400) {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb_100)
        } else if (string.length >= 400 && string.length < 800) {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb_500_200)
        } else {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb)
        }

        tv_sticker.setText(string)
        addMovableItemOnView(tv_sticker)

    }

    private fun addMovableItemOnView(any: View) {
        b.cardsharesaveid.addView(any)
    }

    private fun removeAddedView(view: View) {
        b.cardsharesaveid.removeView(view)
    }


}