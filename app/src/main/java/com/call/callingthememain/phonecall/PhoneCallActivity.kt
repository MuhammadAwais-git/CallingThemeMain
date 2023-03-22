package com.call.callingthememain.phonecall

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.call.callingthememain.R
import com.call.callingthememain.constanatClass.Constants
import com.call.callingthememain.constanatClass.ImageUtils
import com.call.callingthememain.databinding.ActivityPhoneCallBinding
import com.call.callingthememain.utils.Time
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


class PhoneCallActivity : AppCompatActivity(), View.OnClickListener, OnLongClickListener {
    private lateinit var binding: ActivityPhoneCallBinding
    var tvCallNumberLabel: TextView? = null
    var tvCallNumber: TextView? = null
    var tvPickUp: TextView? = null
    var micClicked = 1
    var speakerClicked: Boolean = false
    var holdClicked: Boolean = false

    var tvSpeaker: TextView? = null
    var tvKeypad: TextView? = null
    var layoutDialpad: RelativeLayout? = null

    private var mOneButton: TextView? = null
    private var mTwoButton: TextView? = null
    private var mThreeButton: TextView? = null
    private var mFourButton: TextView? = null
    private var mFiveButton: TextView? = null
    private var mSixButton: TextView? = null
    private var mSevenButton: TextView? = null
    private var mEightButton: TextView? = null
    private var mNineButton: TextView? = null
    private var mZeroButton: TextView? = null
    private var mStarButton: TextView? = null
    private var mPoundButton: TextView? = null
    private var mPhoneNumberField: EditText? = null
    var btnDialpad: ImageButton? = null
    var mDeleteButton: ImageButton? = null
    var mVideoBtn: ImageButton? = null
    var tvHold: TextView? = null
    var tvHangupDialpad: TextView? = null


//    var tvMic: TextView? = null
    /* var tvPickUp: TextView? = null
     var tvPickUp: TextView? = null*/

    var tvCallingTime: TextView? = null
    var tvHangUp: TextView? = null
    var tvHangUpIncoming: TextView? = null
    private var phoneCallManager: PhoneCallManager? = null
    private var callType: CallType? = null
    private var phoneNumber: String? = null
    private var onGoingCallTimer: Timer? = null
    private var callingTime = 0
    var circleImageView: CircleImageView? = null
    var contactPhoto: Bitmap? = null
    var callLayout: LinearLayout? = null
    var callBackgroundLayout: ConstraintLayout? = null
    var incomingCallLayout: RelativeLayout? = null
    var prfs: SharedPreferences? = null
    var imagePath: String? = null
    var position = 0
    var status = false

    var pathfromprefrences = ""
    var themeType: Int = 1

    //flash light
    private var mCameraManager: CameraManager? = null
    var cameraId: String? = null
    var progressValue = 0
    private var flashCondition = true
    private var mHandler: Handler? = null
    private var mInterval: Long = 0
    var time: Time? = null
    var freq = 0
    var switchFlashOn: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //turn on lock Screen
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )


        prfs = getSharedPreferences(packageName, MODE_PRIVATE)
        mCameraManager = this.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        initData()
        initView()
        registerBroadcast()

        switchFlashOn = prfs!!.getBoolean("light", true)
        setFlashOn(switchFlashOn)
        Log.d(TAG, "onCreate: switchFlashOn $switchFlashOn")


    }

    fun setFlashOn(enable: Boolean) {

        Log.d("FlashLgzzzz:", "setFlashOn: $enable")
//        if (!enable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    time = Time()
                    mHandler = Handler()

                    try {
                        cameraId = mCameraManager?.cameraIdList?.get(0)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                    mCameraManager!!.setTorchMode(cameraId!!, enable)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }
//        }

    }

    private fun seekBarMethod() {

            progressValue = 10
        freq = progressValue
        time!!.setSleepTime(freq)
        mInterval = time!!.sleepTime
        if (progressValue == 0) {
            stopRepeatingTask()
        } else {
            startRepeatingTask()
        }

    }


    fun startRepeatingTask() {
        mStatusChecker.run()
    }

    fun stopRepeatingTask() {
        mHandler?.removeCallbacks(mStatusChecker)
        Log.d(TAG, "stopRepeatingTask: ")
    }

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            Log.d(TAG, "run: ")
            try {
                if (flashCondition) {
                    flashCondition = false
                    setFlashOn(switchFlashOn)
//                    binding.imgFlashOn.visibility = View.GONE

                } else {
                    flashCondition = true
                    setFlashOn(false)
                }
            } finally {

                mHandler?.postDelayed(this, mInterval)
            }
        }
    }


    var broadcastReceiver: BroadcastReceiver? = null
    private fun registerBroadcast() {

        broadcastReceiver = object : BroadcastReceiver() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceive(context: Context, intent: Intent) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                if (intent.getBooleanExtra(Constants.DISCONNECT, false)) {
                    finish()
                }
            }
        }
        val filter = IntentFilter(Constants.DISCONNECT_BROADCAST)
        registerReceiver(broadcastReceiver, filter)
    }


    //////////////////////////// initialize the data from shared prefrences /////////////////////////////////////////////
    private fun initData() {
        phoneCallManager = PhoneCallManager(this)
        onGoingCallTimer = Timer()
        if (intent != null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            Log.d(TAG, "initData:phoneNumber$phoneNumber ")
            callType = intent.getSerializableExtra(Intent.EXTRA_MIME_TYPES) as CallType?
            status = intent.getBooleanExtra("fromappDialer", false)
            Log.d(TAG, "initData: Status value =>  $status")
        }

        if (prfs != null) {
            imagePath = prfs!!.getString("image_path", null)
            position = prfs!!.getInt("caller_background_position", -1)
        }
    }

    ///////////////////////////////// initialize the view /////////////////////////////////////////////////////////////////
    @SuppressLint("ResourceType")
    private fun initView() {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //hide navigationBar
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.decorView.systemUiVisibility = uiOptions
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

        //2
        tvCallNumberLabel = findViewById(R.id.tv_call_number_label)
        //3
        tvCallNumber = findViewById(R.id.tv_call_number)
        tvPickUp = findViewById(R.id.tv_phone_pick_up_incoming)

//        tvMic = findViewById(R.id.btn_mic)
        //4
        tvCallingTime = findViewById(R.id.tv_phone_calling_time)
        //1
        circleImageView = findViewById(R.id.profile_image_phone_call)
        callBackgroundLayout = findViewById(R.id.caller_background_layout)
        incomingCallLayout = findViewById(R.id.layout_before_pickup_call)
        callLayout = findViewById(R.id.layout_call)
        tvHangUpIncoming = findViewById(R.id.tv_phone_hang_up_incoming)
        tvHangUp = findViewById(R.id.tv_phone_hang_up)
        tvSpeaker = findViewById(R.id.btn_speaker)
        tvKeypad = findViewById(R.id.btn_keypad)
        layoutDialpad = findViewById(R.id.layout_dialpad_caller)
        mOneButton = findViewById(R.id.one)
        mTwoButton = findViewById(R.id.two)
        mThreeButton = findViewById(R.id.three)
        mFourButton = findViewById(R.id.four)
        mFiveButton = findViewById(R.id.five)
        mSixButton = findViewById(R.id.six)
        mSevenButton = findViewById(R.id.seven)
        mEightButton = findViewById(R.id.eight)
        mNineButton = findViewById(R.id.nine)
        mZeroButton = findViewById(R.id.zero)
        mStarButton = findViewById(R.id.asterisk)
        mPoundButton = findViewById(R.id.hash)
        btnDialpad = findViewById(R.id.btn_dialpad_hide)
        btnDialpad = findViewById(R.id.btn_dialpad_hide)
        tvHold = findViewById(R.id.btn_hold)
        tvHangupDialpad = findViewById(R.id.tv_phone_hang_up_dialpad)


        /////////////////////////////////////////////////// DIALPAD ////////////////////////////////////////////
        mPhoneNumberField = findViewById(R.id.et_phone_number)
        mPhoneNumberField?.setRawInputType(InputType.TYPE_NULL)
        mPhoneNumberField?.isFocusable = true
        mDeleteButton = findViewById<ImageButton>(R.id.deleteButton)


        pathfromprefrences = prfs!!.getString("ThemePath", "")!!
        themeType = prfs!!.getInt("ThemeType", 1)



        if (themeType == 1) {
            Log.d(TAG, "initView: aya video me")
            binding.previewVideoOnCall.visibility = View.VISIBLE
            //video
            val mediaController = MediaController(this)
            mediaController.setAnchorView(binding.previewVideoOnCall)
            val uri = Uri.parse(pathfromprefrences)

            Log.d("TAG", "onCreate:getSharedPreferences .isNotEmpty() $pathfromprefrences")

            binding.previewVideoOnCall.setMediaController(mediaController)
            binding.previewVideoOnCall.setVideoURI(uri)
            binding.previewVideoOnCall.requestFocus()
            binding.previewVideoOnCall.start()
            binding.previewVideoOnCall.setMediaController(null)
            binding.previewVideoOnCall.setOnCompletionListener {

                binding.previewVideoOnCall.seekTo(0)
                binding.previewVideoOnCall.start()
            }

        } else {
            Log.d(TAG, "initView: aya img me")
            binding.previewVideoOnCall.visibility = View.GONE
            callBackgroundLayout?.background = null
            val d: Drawable =
                BitmapDrawable(resources, ImageUtils.getBitmapFromCustomPath(pathfromprefrences))
            callBackgroundLayout?.background = d

        }

        //  }
        tvCallNumber?.text = CallListenerService.formatPhoneNumber(phoneNumber)
        try {

            contactPhoto = retrieveContactPhoto(applicationContext, phoneNumber)
            if (contactPhoto != null) {
                circleImageView?.setImageBitmap(contactPhoto)
            } else {
                val defaultPhoto = BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_contacts_dark
                )
                circleImageView?.setImageBitmap(defaultPhoto)
            }
        } catch (e: NullPointerException) {

        }
        setClickListener()

        //Incoming call
        if (callType == CallType.CALL_IN) {

            tvCallNumberLabel?.text = getContactName(phoneNumber, this)
            incomingCallLayout?.visibility = View.VISIBLE
            callLayout?.visibility = View.GONE
//            if (switchFlashOn==true) {
                setFlashOn(switchFlashOn)
                seekBarMethod()
//            }

        } else {
            Log.d("SETTINGSFRAGMENT", "caller name=" + getContactName(phoneNumber, this))
            tvCallNumberLabel?.text = getContactName(phoneNumber, this)
        }
    }

    fun setClickListener() {
        tvPickUp!!.setOnClickListener(this)
        tvHangUp!!.setOnClickListener(this)
        tvHangUpIncoming!!.setOnClickListener(this)
//        tvMic!!.setOnClickListener(this)
        tvSpeaker!!.setOnClickListener(this)
        tvKeypad!!.setOnClickListener(this)
        btnDialpad!!.setOnClickListener(this)

        tvHold!!.setOnClickListener(this)

        mOneButton!!.setOnClickListener(this)
        mTwoButton!!.setOnClickListener(this)
        mThreeButton!!.setOnClickListener(this)
        mFourButton!!.setOnClickListener(this)
        mFiveButton!!.setOnClickListener(this)
        mSixButton!!.setOnClickListener(this)
        mSevenButton!!.setOnClickListener(this)
        mEightButton!!.setOnClickListener(this)
        mNineButton!!.setOnClickListener(this)
        mZeroButton!!.setOnClickListener(this)
        mStarButton!!.setOnClickListener(this)
        mPoundButton!!.setOnClickListener(this)
        mDeleteButton!!.setOnClickListener(this)
//        mVideoBtn!!.setOnClickListener(this)
        mDeleteButton!!.setOnLongClickListener(this)
        mZeroButton!!.setOnLongClickListener(this)
        tvHangupDialpad!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.tv_phone_pick_up_incoming -> {
                stopRepeatingTask()
                setFlashOn(false)
                phoneCallManager!!.answer()
                incomingCallLayout!!.visibility = View.GONE
                callLayout!!.visibility = View.VISIBLE
                tvCallingTime!!.visibility = View.VISIBLE
                onGoingCallTimer!!.schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            callingTime++
                            tvCallingTime!!.text = "calling：" + getCallingTime()
                        }
                    }
                }, 0, 1000)
            }
            R.id.tv_phone_hang_up ->
                if (status) {
                    stopRepeatingTask()
                    setFlashOn(false)
                    phoneCallManager!!.disconnect()
                    stopTimer()
                    onBackPressed()
                } else {
                    Log.d(TAG, "onClick: call coming from outside app")
                    stopRepeatingTask()
                    setFlashOn(false)
                    phoneCallManager!!.disconnect()
                    stopTimer()
                    finish()
                }
            /*   R.id.btn_mic -> {
                   if (micClicked == 1) {
                       tvMic!!.text = resources.getString(R.string.btn_mic_off_name)
                       phoneCallManager!!.muteMicroPhone()
                       tvMic!!.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_mic_off, 0, 0)
                       micClicked++
                       Toast.makeText(
                           applicationContext,
                           resources.getString(R.string.microphone_off_message),
                           Toast.LENGTH_SHORT
                       ).show()

                   }
                   if (micClicked == 2) {
                       tvMic!!.text = resources.getString(R.string.btn_mic_name)
                       phoneCallManager!!.openMicroPhone()
                       tvMic!!.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_mic, 0, 0)
                       Toast.makeText(
                           applicationContext,
                           resources.getString(R.string.microphone_on_message),
                           Toast.LENGTH_SHORT
                       ).show()
                       micClicked = 1
                   }
               }*/
            R.id.btn_speaker -> {
                if (!speakerClicked) {



                    phoneCallManager!!.loudSpeaker()
                    tvSpeaker!!.text = resources.getString(R.string.btn_speaker_off_name)
                    tvSpeaker!!.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_volume_off, 0, 0)
                    speakerClicked=true

                }
                else{

                    phoneCallManager!!.turnOffSpeaker()
                    tvSpeaker!!.text = resources.getString(R.string.btn_speaker_name)
                    tvSpeaker!!.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_volume_up, 0, 0)

                }
            }
            /*    R.id.btn_bluetooth -> if (PhoneCallActivity.isBluetoothHeadsetConnected()) {
                    phoneCallManager!!.turnOnBluetooth()
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.bluetooth_calling_on),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.no_bluetooth_device),
                        Toast.LENGTH_LONG
                    ).show()
                }*/
            R.id.btn_hold -> {
                if (!holdClicked) {
                    Log.d(TAG, "onClick: holdClicked 1")
                    tvHold?.text = resources.getString(R.string.btn_unhold_name)
                    phoneCallManager!!.putCallOnHold()
                    tvHold?.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        R.drawable.ic_play_arrow,
                        0,
                        0
                    )
                    holdClicked=true


                }
                else{
                    tvHold?.text = resources.getString(R.string.btn_hold_name)
                    phoneCallManager!!.removeCallFromHold()
                    tvHold?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_pause, 0, 0)

                    holdClicked = false
                }
            }
            R.id.btn_keypad -> try {
                val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                if (layoutDialpad!!.visibility == View.GONE) {
                    layoutDialpad!!.visibility = View.VISIBLE
                    callLayout!!.visibility = View.GONE
                    layoutDialpad!!.startAnimation(slideUp)
                }
            } catch (ee: Exception) {
                ee.printStackTrace()
            }
            R.id.btn_dialpad_hide -> {
                layoutDialpad!!.visibility = View.GONE
                callLayout!!.visibility = View.VISIBLE
            }
            R.id.tv_phone_hang_up_dialpad -> if (status) {
                stopRepeatingTask()
                setFlashOn(false)
//                seekBarMethod()
                phoneCallManager!!.disconnect()
                stopTimer()
                finish()
                /*startActivity(new Intent(PhoneCallActivity.this, MainActivity.class));
                    PhoneCallActivity.this.finish();*/
            } else {
                phoneCallManager!!.disconnect()
                stopTimer()
            }
            /* R.id.btn_video -> {
                 val network: String = getNetworkClass(this)
                 val number: String = mPhoneNumberField.getText().toString()
                 if (network == "3G" || network == "4G") {
                     if (!number.isEmpty()) {
                         try {
                             val callIntent = Intent("com.android.phone.videocall")
                             callIntent.putExtra("videocall", true)
                             callIntent.data = Uri.parse("tel:$number")
                             startActivity(callIntent)
                         } catch (e: ActivityNotFoundException) {
                             Toast.makeText(
                                 applicationContext,
                                 resources.getString(R.string.no_application_for_video),
                                 Toast.LENGTH_LONG
                             ).show()
                         }
                     } else {
                         Toast.makeText(
                             applicationContext,
                             resources.getString(R.string.enter_number_first),
                             Toast.LENGTH_LONG
                         ).show()
                     }
                 } else {
                     Toast.makeText(
                         applicationContext,
                         resources.getString(R.string.sim_card),
                         Toast.LENGTH_LONG
                     ).show()
                 }
             }*/
            R.id.tv_phone_hang_up_incoming -> {
                phoneCallManager!!.disconnect()
                stopTimer()
                phoneCallManager!!.disconnect()
                stopTimer()
                finish()
                stopRepeatingTask()
                setFlashOn(false)
            }
            /*  R.id.btn_extra_volume -> if (speakerClicked == 2) {
                  Toast.makeText(
                      applicationContext,
                      resources.getString(R.string.loudspeaker_activated),
                      Toast.LENGTH_LONG
                  ).show()
              } else {
                  phoneCallManager!!.extraVolume()
                  tvExtraVolume.setCompoundDrawablesWithIntrinsicBounds(
                      0,
                      R.drawable.ic_extra_volume_on_36,
                      0,
                      0
                  )
              }*/
            R.id.one -> {
                keyPressed(KeyEvent.KEYCODE_1)
                return
            }
            R.id.two -> {
                keyPressed(KeyEvent.KEYCODE_2)
                return
            }
            R.id.three -> {
                keyPressed(KeyEvent.KEYCODE_3)
                return
            }
            R.id.four -> {
                keyPressed(KeyEvent.KEYCODE_4)
                return
            }
            R.id.five -> {
                keyPressed(KeyEvent.KEYCODE_5)
                return
            }
            R.id.six -> {
                keyPressed(KeyEvent.KEYCODE_6)
                return
            }
            R.id.seven -> {
                keyPressed(KeyEvent.KEYCODE_7)
                return
            }
            R.id.eight -> {
                keyPressed(KeyEvent.KEYCODE_8)
                return
            }
            R.id.nine -> {
                keyPressed(KeyEvent.KEYCODE_9)
                return
            }
            R.id.zero -> {
                keyPressed(KeyEvent.KEYCODE_0)
                return
            }
            R.id.hash -> {
                keyPressed(KeyEvent.KEYCODE_POUND)
                return
            }
            R.id.asterisk -> {
                keyPressed(KeyEvent.KEYCODE_STAR)
                return
            }
            R.id.deleteButton -> {
                keyPressed(KeyEvent.KEYCODE_DEL)
                return
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        when (v.id) {
            R.id.deleteButton -> {
                val digits = mPhoneNumberField!!.text
                digits.clear()
                return true
            }
            R.id.zero -> {
                keyPressed(KeyEvent.KEYCODE_PLUS)
                return true
            }
        }
        return false
    }

    private fun getCallingTime(): String {
        val minute = callingTime / 60
        val second = callingTime % 60
        return (if (minute < 10) "0$minute" else minute).toString() +
                ":" +
                if (second < 10) "0$second" else second
    }

    private fun stopTimer() {
        if (onGoingCallTimer != null) {
            onGoingCallTimer!!.cancel()
        }
        callingTime = 0
    }

    override fun onDestroy() {
        super.onDestroy()
//        prfs.edit().clear().commit()
        phoneCallManager!!.destroy()
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
        }
//        editor!!.clear()
//        editor!!.apply()

        stopRepeatingTask()
        setFlashOn(false)
        Log.d(TAG, "onDestroy: ")

//        seekBarMethod()
    }

    private fun keyPressed(keyCode: Int) {
//        mVibrator.vibrate(DURATION);
        val event = KeyEvent(KeyEvent.ACTION_DOWN, keyCode)
        mPhoneNumberField?.onKeyDown(keyCode, event)
    }

    fun getContactName(phoneNumber: String?, context: Context): String {
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        val projection = arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME)
        var contactName = ""
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0)
            }
            cursor.close()
        }
        return contactName
    }

    fun loadImageFromStorage(path: String?) {
        try {
            val f = File(path)
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            val d: Drawable = BitmapDrawable(resources, b)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                callBackgroundLayout!!.background = d
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "SETTINGSFRAGMENT"

        //////////////////// this intent is started by service for incoming call ///////////////////////////////////////
        @JvmStatic
        fun actionStart(
            context: Context, phoneNumber: String?,
            callType: CallType?
        ) {
            val intent = Intent(context, PhoneCallActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_MIME_TYPES, callType)
            intent.putExtra(Intent.EXTRA_PHONE_NUMBER, phoneNumber)
            context.startActivity(intent)
        }

        fun retrieveContactPhoto(context: Context, number: String?): Bitmap {
            val contentResolver = context.contentResolver
            var contactId: String? = null
            val uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number)
            )
            val projection =
                arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID)
            val cursor = contentResolver.query(
                uri,
                projection,
                null,
                null,
                null
            )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    contactId =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID))
                }
                cursor.close()
            }
            var photo = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_contacts_dark
            )
            ////////////////////// if contact is not saved then id will be null and there will be no picture of contact/////////////////////////////////////////////////
            try {
                if (contactId == null) {
                    Log.d("PhoneCall", "null")
                } else {
                    val inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                        context.contentResolver,
                        ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI,
                            contactId.toLong()
                        )
                    )
                    ///////////////////////////// if contact is saved but picture is not assigned to contact //////////////////////////////////////
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream)
                        assert(inputStream != null)
                        inputStream.close()
                    } else {
                        Log.d("PhoneCall", "null")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return photo
        }
    }


}