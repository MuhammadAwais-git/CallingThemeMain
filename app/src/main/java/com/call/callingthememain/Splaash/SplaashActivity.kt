package com.example.navigatorapp.Splaash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.call.callingthememain.MainActivity
import com.call.callingthememain.R
import kotlinx.android.synthetic.main.activity_phone_call.*
import kotlinx.android.synthetic.main.activity_splaash.*


class SplaashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splaash)

        //video
        val mediaController = MediaController(this)
        mediaController.setAnchorView(splashVideo)
//        val uri = Uri.parse(pathfromprefrences)


        splashVideo.setMediaController(mediaController)
        splashVideo.setVideoURI(
            Uri.parse(
                "android.resource://" + packageName + "/" + R.raw.splashvid
            )
        )
        splashVideo.requestFocus()
        splashVideo.start()
        splashVideo.setMediaController(null)
        splashVideo.setOnCompletionListener {

            splashVideo.seekTo(0)
            splashVideo.start()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

    }


}