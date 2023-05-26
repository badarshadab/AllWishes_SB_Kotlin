package com.examp.allwishes.ui.activity

import androidx.appcompat.app.AppCompatActivity


class CreateCardActivity : AppCompatActivity() {

//    var fontlist: String = ""
//
//
//    var stickerViewclass: StickerView? = null
//    private var SECTION_NAME: String = "section_name"
//    private var CATEGORTY_NAME: String = "category_name"
//    private var FAMILY_SUBCATEGOTY_NAME: String = "subcategory_name"
//
//
//    private var CARDVIEW: String = "view"
//
//    var dtextView: TextView? = null
//    var quotetext: TextView? = null
//    var addstr: String? = null
//    var stickerview: ImageView? = null
//
//
//    private var addbgbinding: AddbgDiloglayoutBinding? = null
//    private var addbgdialog: Dialog? = null
//    private var addbgcolordialog: Dialog? = null
//    private var colorpickdialog: Dialog? = null
//    private var addtextdialog: Dialog? = null
//    lateinit var quotesbinding: QuoteslistdlayoutBinding
//    private var quotelistdialog: Dialog? = null
//    lateinit var stickerBinding: StickerdiloglayoutBinding
//    private var stickerdialog: Dialog? = null
//
//    private val GALLERY: Int = 1
//    private val CAMERA: Int = 2
//    var aalphaTileview: AlphaTileView? = null
//    var mdefult: Int? = null
//
//    lateinit var outview: Animation
//    lateinit var inview: Animation
//
//    var path: String = ""
//    var catName: String = ""
//
//    lateinit var stickerImageView: StickerImageView
//
//    private lateinit var createCardViewModel: CreateCardViewModel
//    lateinit var binding: ActivityCreateBinding
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCreateBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
////        binding = DataBindingUtil.setContentView(this, R.layout.activity_create)
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        val actionbar = supportActionBar
//        actionbar?.setDisplayHomeAsUpEnabled(true)
//
//        var sectionName: String = intent.getStringExtra(SECTION_NAME).toString()
//        catName = intent.getStringExtra(CATEGORTY_NAME).toString()
//        var subcatName: String = intent.getStringExtra(FAMILY_SUBCATEGOTY_NAME).toString()
//
//        path = if (sectionName == "family") {
//            "$sectionName/$catName/$subcatName"
//        } else {
//            "$sectionName/$catName"
//        }
//
//
//
//        quotetext = TextView(this)
//        quotetext?.layoutParams = RelativeLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
//        quotetext?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5f)
//        quotetext?.gravity = (Gravity.BOTTOM)
//
//
//        binding.fontcolorlistlayout.visibility = View.GONE
//
//        createCardViewModel = ViewModelProvider(this)[CreateCardViewModel::class.java]
//        createCardViewModel.colorList()
//        createCardViewModel.fontList()
//        createCardViewModel.gradientList()
//
//
//        binding.nextBtn.visibility = View.GONE
//
//        binding.nextBtn.setOnClickListener {
//            binding.create.visibility = View.GONE
//            var viewdata = AppUtils.getBitmapFromView(binding.cardsharesaveid)
//            var intent = Intent(this@CreateCardActivity, CardpreviewActivity::class.java)
//            intent.putExtra("bitimgkey", saveBitmap(viewdata))
//            startActivity(intent)
////            println("HAMR TUM "+saveBitmap(viewdata))
//        }
//
//
//        binding.create.setOnClickListener {
//            addbgDilog()
//            binding.nextBtn.visibility = View.VISIBLE
//        }
//
//
//    }
//
//
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return super.onSupportNavigateUp()
//    }
//
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == GALLERY) {
//            binding.createimageview.setImageURI(data?.data)
//        } else if (requestCode == CAMERA) {
//            val photo = data!!.extras!!["data"] as Bitmap?
//            // Set the image in imageview for display
//            binding.createimageview.setImageBitmap(photo)
//        }
//    }
//
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            1 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
//                                PackageManager.PERMISSION_GRANTED)
//                    ) {
//                        AppUtils.captercamera(this@CreateCardActivity)
//                    }
//                }
//                return
//            }
//        }
//    }
//
//
//    fun iconButton(view: View) {
//        if (view == binding.addbgid) {
//
//            binding.textcolorid.setCompoundDrawablesWithIntrinsicBounds(
//                0,
//                R.drawable.textcolor,
//                0,
//                0
//            );
//            binding.stickersid.setCompoundDrawablesWithIntrinsicBounds(
//                0,
//                R.drawable.stickers,
//                0,
//                0
//            );
//            binding.addtextid.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.addtext, 0, 0)
//
//            binding.addbgid.setCompoundDrawablesWithIntrinsicBounds(
//                0,
//                R.drawable.addbg_selectbtn,
//                0,
//                0
//            )
//
//
//            binding.toolbartitle.apply {
//                this.setText(R.string.add_bgtitle)
//
//            }
//            binding.fontcolorlistlayout.visibility = View.GONE
//            addbgDilog()
//            binding.nextBtn.visibility = View.VISIBLE
//
//            val mListener = MultiTouchListener()
//            mListener.minimumScale = 0.1f
//            binding.createimageview.setOnTouchListener(mListener)
//
//
//        } else if (view == binding.addtextid) {
//
//            binding.fontcolorlistlayout.visibility = View.GONE
//            binding.create.visibility = View.GONE
//            binding.toolbartitle.setText(R.string.add_text)
//            addTextDilog()
//
//            dtextView = TextView(this)
//
//            dtextView?.layoutParams = RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
////            dtextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP,13f)
//
//            dtextView?.gravity = (Gravity.CENTER)
//
//            val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.font11b)
//            dtextView?.setTypeface(typeface)
//            dtextView?.textSize = 40f
//
//            dtextView?.setOnTouchListener(MultiTouchListener())
//            binding.cardrootlayout.apply {
//                this.addView(dtextView)
//            }
////            layout?.removeView(layout)
//
//            // COLORE LIST
//
//            //Font LiSt
////
//
//
//            binding.nextBtn.visibility = View.VISIBLE
//
//
//        } else if (view == binding.quoteid) {
//
//
//            binding.create.visibility = View.GONE
//            binding.toolbartitle.setText(R.string.text_Quotes)
//
//            quotesListDilog()
//            binding.nextBtn.visibility = View.VISIBLE
//
//
//        } else if (view == binding.fontsid) {
//            binding.create.visibility = View.GONE
//
//
//
//
//
//            binding.toolbartitle.setText(R.string.fonts)
//            binding.fontcolorlistlayout.visibility = View.VISIBLE
//            binding.fontcolorlistlayout.setBackgroundColor(Color.WHITE)
//
//
//
//            fontlist = "fontABCD"
//            val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_ups)
//            binding.fontcolorlistlayout.startAnimation(anim)
//            binding.listfontcolorrecyclerview.apply {
//                createCardViewModel.fontlivedata.observe(
//                    this@CreateCardActivity,
//                    androidx.lifecycle.Observer { fontdatalist ->
//                        val fontsAdapter = FontsAdapter(
//                            fontdatalist,
//                            this@CreateCardActivity,
//                            object : OnItemClickListener {
//                                override fun onClick(position: Int) {
//                                    val fontstyle = ResourcesCompat.getFont(
//                                        context,
//                                        fontdatalist[position].fontstyle!!
//                                    )
//                                    dtextView?.typeface = fontstyle
//                                    quotetext?.typeface = fontstyle
//                                }
//                            })
//                        fontdatalist.shuffle()
//                        adapter = fontsAdapter
//
//                    })
//
//            }
//
//            binding.nextBtn.visibility = View.VISIBLE
//
//        } else if (view == binding.textcolorid) {
//            binding.create.visibility = View.GONE
//
//            binding.toolbartitle.setText(R.string.text_color)
//            binding.fontcolorlistlayout.visibility = View.VISIBLE
//            binding.fontcolorlistlayout.setBackgroundColor(Color.WHITE)
//
////            binding.fontcolorlistlayout.visibility = View.GONE
//            val anim2: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_left)
//            binding.fontcolorlistlayout.startAnimation(anim2)
//
//            binding.listfontcolorrecyclerview.apply {
//                createCardViewModel.colorlivedata.observe(
//                    this@CreateCardActivity,
//                    androidx.lifecycle.Observer { fonttextcolor ->
//                        val textAdapter = TextChooseColorAdapter(
//                            fonttextcolor,
//                            this@CreateCardActivity,
//                            object : OnItemClickListener {
//                                override fun onClick(position: Int) {
//                                    if (position == 0) {
//                                        textPickDilog()
//                                    } else {
//                                        dtextView?.setTextColor(fonttextcolor[position].color)
//                                        quotetext?.setTextColor(fonttextcolor[position].color)
//                                    }
//                                }
//                            })
//                        adapter = textAdapter
//
//                    })
//            }
//
//            binding.nextBtn.visibility = View.VISIBLE
//
//        } else {
//            binding.create.visibility = View.GONE
//
//
//            binding.toolbartitle.setText(R.string.stickers)
//
//            stickersDilog()
//            binding.nextBtn.visibility = View.VISIBLE
//
//        }
//
//
//    }
//
//    private fun addbgDilog() {
//
//
//        addbgbinding = AddbgDiloglayoutBinding.inflate(layoutInflater)
//        addbgdialog = Dialog(this, R.style.addbgWideDialog)
//        addbgdialog?.setContentView(addbgbinding!!.root)
//        addbgdialog?.setCancelable(true)
//        addbgdialog!!.window?.attributes?.windowAnimations = R.style.DialogTheme4
//        addbgdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        addbgdialog?.window?.setGravity(Gravity.BOTTOM)
//
//        addbgdialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme4;
//
//
//
//        addbgbinding?.addbggoodmg?.text = catName.capitalize()
//
//
//
//        AppUtils.textColorGradent(addbgbinding!!.chooseBg)
//        AppUtils.textColorGradent(addbgbinding!!.colorid)
//        AppUtils.textColorGradent(addbgbinding!!.galleryid)
//        AppUtils.textColorGradent(addbgbinding!!.cameraid)
//        AppUtils.textColorGradent(addbgbinding!!.addbggoodmg)
//
////        SmAdds.showBannerAdd(this@CreateCardActivity, addbgbinding!!.bottomaddscbackid)
//
//
//        addbgbinding?.colorid?.setOnClickListener {
//            colorDilog()
//            addbgdialog?.dismiss()
//        }
//
//        addbgbinding?.galleryid?.setOnClickListener {
//            binding.create.visibility = View.GONE
//            binding.createimageview.setOnTouchListener(MultiTouchListener())
//            AppUtils.getpicGallery(this@CreateCardActivity)
//            addbgdialog?.dismiss()
//
//        }
//
//        addbgbinding?.cameraid?.setOnClickListener {
//
//            val mListener = MultiTouchListener()
//            mListener.minimumScale = 0.1f
//            binding.createimageview.setOnTouchListener(mListener)
////            .setOnTouchListener(MultiTouchListener())
//
//            AppUtils.camshow(this@CreateCardActivity)
//            addbgdialog?.dismiss()
//        }
//
//
//        addbgbinding?.addbgrecycleid.apply {
//            createCardViewModel.getALLGif(path)
//                ?.observe(this@CreateCardActivity, androidx.lifecycle.Observer { datalist ->
//                    val addbgCardAdapter = AddbgCardAdapter(
//                        datalist,
//                        this@CreateCardActivity,
//                        object : OnItemClickListener {
//                            override fun onClick(position: Int) {
//                                binding.create.visibility = View.GONE
//                                Glide.with(this@CreateCardActivity).load(datalist[position])
//                                    .centerCrop()
//                                    .placeholder(R.drawable.loading)
//                                    .into(binding.createimageview)
//                                addbgdialog?.dismiss()
//                            }
//                        })
//                    this?.adapter = addbgCardAdapter
//                    addbgbinding?.bgprogressbarid?.progresbarid?.visibility = View.GONE
//                })
//        }
//
//        addbgdialog?.show()
//    }
//
//    fun colorDilog() {
//
//
//        val colordilogslayout = layoutInflater.inflate(R.layout.addbg_colorlayout, null)
//        addbgcolordialog = Dialog(this, R.style.addbgWideDialog)
//
//        addbgcolordialog?.setContentView(colordilogslayout)
//        addbgcolordialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme2;
//
//        addbgcolordialog?.setCancelable(true)
//        addbgcolordialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        addbgcolordialog?.window?.setGravity(Gravity.BOTTOM)
//
//
//        var dilogrecycler1: RecyclerView = colordilogslayout.findViewById(R.id.onecolorrecyView)
//        val coloranim: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_ups)
//        dilogrecycler1.startAnimation(coloranim)
//        createCardViewModel.colorlivedata.observe(
//            this@CreateCardActivity,
//            androidx.lifecycle.Observer { bgcolorlist ->
//
//                val colorAdapter = ChooseColorAdapter(
//                    bgcolorlist,
//                    this@CreateCardActivity,
//                    object : OnItemClickListener {
//                        override fun onClick(position: Int) {
//                            if (position == 0) {
//                                pickDilog()
//                                addbgcolordialog?.dismiss()
//                            } else {
//                                binding.createimageview.setImageDrawable(null)
//                                binding.createimageview.setBackgroundColor(bgcolorlist[position].color)
//                                addbgcolordialog?.dismiss()
//                            }
//                        }
//                    })
//                dilogrecycler1.adapter = colorAdapter
//
//            })
//
//        val dilogrecycler3: RecyclerView = colordilogslayout.findViewById(R.id.gridentrecycView)
//
//
//
//        dilogrecycler3.startAnimation(coloranim)
//        //----
//
//
//        dilogrecycler3.apply {
//            createCardViewModel.gradientlivedata.observe(
//                this@CreateCardActivity,
//                androidx.lifecycle.Observer { gradinentdatalist ->
//                    val chooseGradCloreAdapter = ChooseGradCloreAdapter(
//                        gradinentdatalist,
//                        this@CreateCardActivity,
//                        object : OnItemClickListener {
//                            override fun onClick(position: Int) {
//                                binding.createimageview.setImageDrawable(null)
//
//                                if (position == 0) {
//                                    gradentpickDilog()
////                                    gradentpickDilog2()
//                                    addbgcolordialog?.dismiss()
//
//                                } else {
//
//                                    val gd = GradientDrawable(
//                                        GradientDrawable.Orientation.TOP_BOTTOM,
//                                        gradinentdatalist[position].gradlist
//                                    )
//                                    gd.cornerRadius = 0f
//
//                                    binding.createimageview.setBackgroundDrawable(gd)
//                                    addbgcolordialog?.dismiss()
//
//                                }
//
//
//                            }
//                        })
//
//                    adapter = chooseGradCloreAdapter
//                })
//        }
//
//        addbgcolordialog?.show()
//
//    }
//
//    fun pickDilog() {
//        val colorpiclayout = layoutInflater.inflate(R.layout.colorpicklayout, null)
//        colorpickdialog = Dialog(this, R.style.WideDialogcolorpic)
//        colorpickdialog?.setContentView(colorpiclayout)
//        colorpickdialog?.setCancelable(true)
//        colorpickdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        colorpickdialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)
//
//
//        val cancelbtn: ImageView = colorpiclayout.findViewById(R.id.cancelbtnid)
//        val colorpickview: ColorPickerView = colorpiclayout.findViewById(R.id.colorPickerView)
//        val alphaSlidebar: AlphaSlideBar = colorpiclayout.findViewById(R.id.alphaSlideBar)
//        val brightnessslide: BrightnessSlideBar = colorpiclayout.findViewById(R.id.brightnessSlide)
//        aalphaTileview = colorpiclayout.findViewById(R.id.alphatileView)
//        val done_btn: TextView = colorpiclayout.findViewById(R.id.okid)
//        val percenTage: TextView = colorpiclayout.findViewById(R.id.percentage)
//        var seekbarid: SeekBar = colorpiclayout.findViewById(R.id.seekbar)
//
//
//
//
//
//        cancelbtn.setOnClickListener {
//            colorpickdialog?.dismiss()
//        }
//
//        val bubbleFlag = BubbleFlag(this)
//        bubbleFlag.flagMode = FlagMode.ALWAYS
//        var envelope2: ColorEnvelope? = null
//        colorpickview.flagView = bubbleFlag
//        colorpickview.setColorListener(
//            ColorEnvelopeListener { envelope: ColorEnvelope, fromUser: Boolean ->
//                envelope2 = envelope
//
//                aalphaTileview?.setPaintColor(envelope.color)
//                Timber.d("color: %s", envelope.hexCode)
////                        percenTage.text = setColorAlpha(100,"#" + envelope.hexCode)
//
//
//            })
//
//        colorpickview.attachAlphaSlider(alphaSlidebar)
//        colorpickview.attachBrightnessSlider(brightnessslide)
//        colorpickview.setLifecycleOwner(this)
//
//
//
//
//
//        done_btn.setOnClickListener {
//            binding.createimageview.setImageResource(0)
//            binding.cardrootlayout.setBackgroundResource(0)
//            binding.create.visibility = View.GONE
//            binding.createimageview.setBackgroundColor(envelope2!!.color)
//            colorpickdialog?.dismiss()
//        }
//
//
//
//        seekbarid.visibility = View.GONE
//        seekbarid.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//
//
//                percenTage.setText("" + progress + "%")
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            }
//
//        })
//
//        colorpickdialog?.show()
//    }
//
//    fun textPickDilog() {
//        val colorpiclayout = layoutInflater.inflate(R.layout.colorpicklayout, null)
//        colorpickdialog = Dialog(this, R.style.WideDialog)
//        colorpickdialog?.setContentView(colorpiclayout)
//        colorpickdialog?.setCancelable(true)
//        colorpickdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        colorpickdialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)
//
//
//        val cancelbtn: ImageView = colorpiclayout.findViewById(R.id.cancelbtnid)
//        val colorpickview: ColorPickerView = colorpiclayout.findViewById(R.id.colorPickerView)
//        val alphaSlidebar: AlphaSlideBar = colorpiclayout.findViewById(R.id.alphaSlideBar)
//        val brightnessslide: BrightnessSlideBar = colorpiclayout.findViewById(R.id.brightnessSlide)
//        aalphaTileview = colorpiclayout.findViewById(R.id.alphatileView)
//        val done_btn: TextView = colorpiclayout.findViewById(R.id.okid)
//        val percenTage: TextView = colorpiclayout.findViewById(R.id.percentage)
//        var seekbarid: SeekBar = colorpiclayout.findViewById(R.id.seekbar)
//
//
//
//
//
//        cancelbtn.setOnClickListener {
//            colorpickdialog?.dismiss()
//        }
//
//        val bubbleFlag = BubbleFlag(this)
//        bubbleFlag.flagMode = FlagMode.ALWAYS
//        var envelope2: ColorEnvelope? = null
//        colorpickview.flagView = bubbleFlag
//        colorpickview.setColorListener(
//            ColorEnvelopeListener { envelope: ColorEnvelope, fromUser: Boolean ->
//                envelope2 = envelope
//
//                aalphaTileview?.setPaintColor(envelope.color)
//                Timber.d("color: %s", envelope.hexCode)
////                        percenTage.text = setColorAlpha(100,"#" + envelope.hexCode)
//
//
//            })
//
//        colorpickview.attachAlphaSlider(alphaSlidebar)
//        colorpickview.attachBrightnessSlider(brightnessslide)
//        colorpickview.setLifecycleOwner(this)
//
//
//
//
//
//        done_btn.setOnClickListener {
//
//            binding.create.visibility = View.GONE
//            dtextView?.setTextColor(envelope2!!.color)
//            quotetext?.setTextColor(envelope2!!.color)
//
//            colorpickdialog?.dismiss()
//        }
//
//
//
//        seekbarid.visibility = View.GONE
//        seekbarid.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//
//
//                percenTage.setText("" + progress + "%")
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            }
//
//        })
//
//        colorpickdialog?.show()
//    }
//
//    fun gradentpickDilog() {
//
//
//        var gradbinding = GradentpicklayoutBinding.inflate(layoutInflater)
//        var gradDialog = Dialog(this, R.style.WideDialogcolorpic)
//        gradDialog?.setContentView(gradbinding.root)
//        gradDialog?.setCancelable(true)
//        gradDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        gradDialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)
//
//        gradbinding.cancelbtnid.setOnClickListener {
//            gradDialog?.dismiss()
//        }
//
//
////        val bubbleFlag = BubbleFlag(this)
////        bubbleFlag.flagMode = FlagMode.ALWAYS
////        var envelopeGr1: ColorEnvelope? = null
////        gradbinding.colorPickerViewgr1.flagView = bubbleFlag
////
////        gradbinding.colorPickerViewgr1.setColorListener(
////            ColorEnvelopeListener { envelope: ColorEnvelope, fromUser: Boolean ->
////                envelopeGr1 = envelope
////
////                Timber.d("color: %s", envelope.hexCode)
////                gradbinding.alphatileView?.setPaintColor(envelope.color)
////
////            })
////
////        gradbinding.colorPickerViewgr1.attachAlphaSlider(gradbinding.graalphaSlideBar)
////        gradbinding.colorPickerViewgr1.setLifecycleOwner(this)
//
//
////--2
////        val bubbleFlag2 = BubbleFlag(this)
////        bubbleFlag2.flagMode = FlagMode.ALWAYS
////        var envelopeGr2: ColorEnvelope? = null
////        gradbinding.colorPickerViewgr2.flagView = bubbleFlag2
////        gradbinding.colorPickerViewgr2.setColorListener(
////            ColorEnvelopeListener {envelope2: ColorEnvelope, fromUser: Boolean ->
////                envelopeGr2 = envelope2
////
////                Timber.d("color: %s", envelope2.hexCode)
////                gradbinding.alphatileView?.setPaintColor(envelope2.color)
////
////            })
////        gradbinding.colorPickerViewgr2.attachAlphaSlider(gradbinding.graalphaSlideBar)
////        gradbinding.colorPickerViewgr2.setLifecycleOwner(this)
//
//
//        val pk = BubbleFlag(this)
//        pk.flagMode = FlagMode.ALWAYS
//
//        var env: ColorEnvelope? = null
//        var env2: ColorEnvelope? = null
//
////        gradbinding.colorPickerViewgr2.flagView = pk
//        gradbinding.colorPickerViewgr1.flagView = pk
//
//        gradbinding.colorPickerViewgr1.setColorListener(
//            ColorEnvelopeListener { envelopet: ColorEnvelope, fromUser: Boolean ->
//                env = envelopet
//
////                Timber.d("color: %s", envelope2.hexCode)
//                gradbinding.alphatileView?.setPaintColor(envelopet.color)
//
//            })
//
//        gradbinding.colorPickerViewgr2.setColorListener(
//            ColorEnvelopeListener { envelopet: ColorEnvelope, fromUser: Boolean ->
//                env2 = envelopet
//                println("RALKLLL " + env2)
//
////                Timber.d("color: %s", envelope2.hexCode)
//                gradbinding.alphatileView?.setPaintColor(envelopet.color)
//
//            })
//
//        gradbinding.colorPickerViewgr1.attachAlphaSlider(gradbinding.graalphaSlideBar)
//        gradbinding.colorPickerViewgr1.setLifecycleOwner(this)
//        gradbinding.colorPickerViewgr2.attachAlphaSlider(gradbinding.graalphaSlideBar)
//        gradbinding.colorPickerViewgr2.setLifecycleOwner(this)
//
//
//        gradbinding.gokid.setOnClickListener {
//            binding.createimageview.setImageResource(0)
//            binding.cardrootlayout.setBackgroundResource(0)
//            binding.create.visibility = View.GONE
//            binding.createimageview.setBackgroundColor(env!!.color)
//            gradDialog?.dismiss()
//        }
//
//        gradDialog?.show()
//    }
//
//    fun gradentpickDilog2() {
//
//
//        var gradentbinding = GradentpickerlayoutBinding.inflate(layoutInflater)
//        var gradentDialog = Dialog(this, R.style.WideDialog)
//        gradentDialog?.setContentView(gradentbinding.root)
//        gradentDialog?.setCancelable(true)
//        gradentDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        gradentDialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)
//
//        gradentDialog?.show()
//    }
//
//
//    fun addTextDilog() {
//        val addtextlayout = layoutInflater.inflate(R.layout.add_text, null)
//        addtextdialog = Dialog(this, R.style.WideDialog)
//        addtextdialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme2;
//        addtextdialog?.setContentView(addtextlayout)
//
//        addtextdialog?.setCancelable(true)
//        addtextdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//
//        var cancelbtn: Button = addtextlayout.findViewById(R.id.cancel_btn)
//        var vancelbtn2: ImageView = addtextlayout.findViewById(R.id.canclebtn2)
//        var addtext: EditText = addtextlayout.findViewById(R.id.textid)
//        var okbtn: Button = addtextlayout.findViewById(R.id.addokbtn)
//
//        cancelbtn.setOnClickListener {
//            addtextdialog?.dismiss()
//        }
//        okbtn.setOnClickListener {
//
//            dtextView?.text = addtext.text.toString()
//
//
//            addtextdialog?.dismiss()
//        }
//
//        vancelbtn2.setOnClickListener {
//            addtextdialog?.dismiss()
//        }
//        addtextdialog?.show()
//    }
//
//    fun quotesListDilog() {
//
//        quotesbinding = QuoteslistdlayoutBinding.inflate(layoutInflater)
//        quotelistdialog = Dialog(this, R.style.WideDialog)
//        quotelistdialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        quotelistdialog?.setContentView(quotesbinding.root)
//        val lout = WindowManager.LayoutParams()
//        lout.copyFrom(quotelistdialog?.getWindow()?.getAttributes())
//        lout.width = WindowManager.LayoutParams.MATCH_PARENT
//        lout.height = WindowManager.LayoutParams.MATCH_PARENT
//        quotelistdialog?.setCancelable(true)
//        quotelistdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        quotelistdialog?.getWindow()?.setAttributes(lout)
//
////        SmAdds.showNativeBannerAdd(this@CreateCardActivity, quotesbinding.bottomaddsccard)
//
//        quotesbinding.quotesbackid.setOnClickListener {
//            quotelistdialog?.dismiss()
//        }
//
//        quotesbinding.recyclerquote.apply {
//            createCardViewModel.getData(path)
//                .observe(this@CreateCardActivity, androidx.lifecycle.Observer { quotedata ->
//                    var quotesAdapter = QuotesAdapter(
//                        quotedata,
//                        this@CreateCardActivity,
//                        object : OnItemClickListener_Quotes {
//                            override fun onClick(position: Int) {
//
//                                quotetext?.text = quotedata[position].toString()
//                                val typeface: Typeface? =
//                                    ResourcesCompat.getFont(this@CreateCardActivity, R.font.font11b)
//                                quotetext?.setTypeface(typeface)
//                                quotetext?.textSize = 30f
//
//                                quotetext?.setOnTouchListener(MultiTouchListener())
//
//                                if (quotetext?.getParent() != null) {
//                                    (quotetext?.getParent() as ViewGroup).removeView(quotetext)
//                                }
//                                binding.cardrootlayout.addView(quotetext)
//                                quotelistdialog?.dismiss()
//
//                            }
//                        })
//                    adapter = quotesAdapter
//                    quotesbinding.quoteprocessid.progresbarid.visibility = View.GONE
//                })
//        }
//
//
//
//
//        quotelistdialog?.show()
//    }
//
//    fun stickersDilog() {
//        stickerBinding = StickerdiloglayoutBinding.inflate(layoutInflater)
//        stickerdialog = Dialog(this, R.style.addbgWideDialog)
//        stickerdialog?.setContentView(stickerBinding.root)
//        stickerdialog?.setCancelable(true)
//
//        stickerdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        stickerdialog?.window?.setGravity(Gravity.BOTTOM)
//        stickerdialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme4;
//
//
////        SmAdds.showBannerAdd(this@CreateCardActivity, stickerBinding.bottomaddscstickerid)
//
//        stickerBinding.stickerrecycleid.apply {
//            createCardViewModel.getALLSticker(path).observe(
//                this@CreateCardActivity,
//                androidx.lifecycle.Observer { datalist ->
//
//                    val stickerAdpter =
//                        StickerAdpter(
//                            datalist,
//                            this@CreateCardActivity,
//                            object : StickerOnItemClick {
//                                override fun onClick(view: View, position: Int) {
////                                    val layout = findViewById<RelativeLayout>(R.id.cardrootlayout)
////                                    stickerview = ImageView(this@CreateCardActivity)
////                                    stickerview?.layoutParams =  RelativeLayout.LayoutParams(400, 400)
////                                    stickerview?.x = 20F // setting margin from left
////                                    stickerview?.y = 20F // setting margin from top
////                                    stickerview?.setOnTouchListener(MultiTouchListener())
////
////                                    Glide.with(this@CreateCardActivity).load(datalist[position])
////                                        .placeholder(R.drawable.loading)
////                                        .skipMemoryCache(true)
////                                        .into(stickerview!!)
//                                    //                                    layout?.addView(stickerview)
//
//                                    stickerImageView = StickerImageView(this@CreateCardActivity)
//                                    stickerImageView.imageBitmap = view?.drawToBitmap()
//                                    binding.cardrootlayout.addView(stickerImageView)
//                                    stickerdialog?.dismiss()
//
//                                }
//
//                            })
//
//                    adapter = stickerAdpter
//                    stickerBinding.backgroundpbarid.progresbarid.visibility = View.GONE
//
//
//                })
//        }
//
//
//        stickerdialog?.show()
//    }
//
//
//    fun saveBitmap(bitmap: Bitmap): String? {
//        var fileName: String? = "ImageName" //no .png or .jpg needed
//        try {
//            val bytes = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//            val fo: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
//            fo.write(bytes.toByteArray())
//            // remember close file output
//            fo.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            fileName = null
//        }
//        return fileName
//    }


}//class brishes


