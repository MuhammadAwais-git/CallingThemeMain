package com.call.callingthememain


import android.app.role.RoleManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.call.callingthememain.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {

        var switchFlashOn = false

    }

    private val REQUEST_CODE = 101

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var flashSwitch: SwitchCompat
    lateinit var callScreenSwitch: SwitchCompat
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListener()



        prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val navHostController =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostController.navController

        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {

                when (destination.id) {
                    R.id.system_Info_Fragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.dashBoardFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.batteryInformationFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.sensorInfpFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.storageInfoFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.displayInfoFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.perviewWallpaperFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.previewCallThemeFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.storageFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    R.id.previewFavouriteFragment -> {
                        imgDrawer.visibility = View.GONE
                        callScreen.visibility = View.GONE
                        coordinatorlayout.visibility = View.GONE
                    }
                    else -> {
                        imgDrawer.visibility = View.VISIBLE
                        callScreen.visibility = View.VISIBLE
                        coordinatorlayout.visibility = View.VISIBLE
                    }
                }
            }
        })

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            val reselectedDestinationId = item.itemId
            navController.popBackStack(reselectedDestinationId, inclusive = false)
        }

/*
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->


            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                }

                R.id.wallpaperFragment -> {
                    navController.navigate(R.id.wallpaperFragment)

                }
                R.id.flashLightCTFragment -> {
                    navController.navigate(R.id.flashLightCTFragment)
                }
                R.id.mobileToolsFragment -> {
                    navController.navigate(R.id.mobileToolsFragment)
                }
            }
            true
        }

*/

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun clickListener() {
        DrawerListener()
        btnStorage.setOnClickListener {
            navController.navigate(R.id.storageFragment)
        }

    }

    private fun DrawerListener() {
        imgDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        apply {
            toggle = ActionBarDrawerToggle(
                this@MainActivity, drawerLayout,
                R.string.open,
                R.string.close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            flashSwitch = navView.findViewById(R.id.flashSwitch)
            callScreenSwitch = navView.findViewById(R.id.callScreenSwitch)
//            navView.findViewById<TextView>(R.id.txt_removeadds).setOnClickListener {
//                Toast.makeText(this@MainActivity, "Smjo Remove ho gye ", Toast.LENGTH_SHORT)
//                    .show()
//            }
            navView.findViewById<TextView>(R.id.txt_moreapps).setOnClickListener {
                Toast.makeText(this@MainActivity, "more ", Toast.LENGTH_SHORT).show()
//                moreOurApp(this@MainActivity)
            }
            navView.findViewById<TextView>(R.id.txt_rateus).setOnClickListener {
                Toast.makeText(this@MainActivity, "rateus ", Toast.LENGTH_SHORT).show()

//                rateUsApp(this@MainActivity)
            }
            navView.findViewById<TextView>(R.id.txt_privacypolicy).setOnClickListener {
                Toast.makeText(this@MainActivity, "privacypolicy ", Toast.LENGTH_SHORT).show()

//                appPrivacyPolicy(this@MainActivity)
            }
            navView.findViewById<TextView>(R.id.txt_share).setOnClickListener {
                Toast.makeText(this@MainActivity, "share ", Toast.LENGTH_SHORT).show()

//                shareUsApp(this@MainActivity)
            }
            navView.findViewById<TextView>(R.id.txt_Feedback).setOnClickListener {
                Toast.makeText(this@MainActivity, "Feedback ", Toast.LENGTH_SHORT).show()

//                feedBack("sonikstudio001@gmail.com")
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }


    fun callScreenSwitch(view: View?) {

        if (callScreenSwitch.isChecked) {

            Log.d("TAG", "toggle: callScreenSwitch isChecked")
        } else {

            /*    val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, "")
                startActivity(intent)*/


        }
    }

    fun flashSwitch(view: View?) {

        if (flashSwitch.isChecked) {
            switchFlashOn = true
            val editor = prefs.edit()
            editor.putBoolean("light", switchFlashOn)
            editor.apply()

            Log.d("TAG", "toggle: flashSwitch isChecked")
        } else {
            Log.d("TAG", "toggle: flashSwitch NotChecked")
            switchFlashOn = false
            val editor = prefs.edit()
            editor.putBoolean("light", switchFlashOn)
            editor.apply()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//                if(requestCode == 101){
//
//        }
        Log.d("TAG", "onActivityResult: code $requestCode ")
        if (requestCode == REQUEST_CODE) {
            Log.d("TAG", "onActivityResult: ")
//            startAppFunction()

        }
    }

    private fun startAppFunction() {
        if (isDefaultPhoneCallApp()) {
            Log.d("TAG", "startAppFunction:  yes")
            return
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                startActivityForResult(intent, REQUEST_CODE)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    Log.d("TAG", "onClick: android 23 and above selected")
                    val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    intent.putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        applicationContext.packageName
                    )
                    startActivityForResult(intent, REQUEST_CODE)
                } catch (ee: Exception) {
                    ee.printStackTrace()
                }
            }
        }
    }

    fun isDefaultPhoneCallApp(): Boolean {
        Log.d("TAG", "isDefaultPhoneCallApp: enter in default phone call")
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val manger = getSystemService(TELECOM_SERVICE) as TelecomManager
                if (manger != null) {
                    val name = manger.defaultDialerPackage
                    return name == packageName
                }
            }
        } catch (ee: java.lang.Exception) {
            ee.printStackTrace()
        }
        return false
    }



    override fun onStart() {
        startAppFunction()
        super.onStart()
    }

    override fun onBackPressed() {
        Log.i("onBackPressed", "back")
        val fragmentId = navController.currentDestination?.id

        if (fragmentId == R.id.homeFragment) {

            val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                finishAffinity()
            }
            val negativeButtonClick = { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
            }
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Exit Application")
                setMessage("Are you sure you want to quit app")
                setPositiveButton("Exit", positiveButtonClick)
                setNegativeButton("Cancel", negativeButtonClick)
                show()
            }

        } else {
            navController.popBackStack()


        }
    }
}
