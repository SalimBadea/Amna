package com.salem.amna.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.salem.amna.R
import com.salem.amna.base.BaseActivity
import com.salem.amna.databinding.ActivityAuthBinding
import com.salem.amna.presentation.common.location.LocationPermission
import com.salem.amna.presentation.common.location.OnLocationPermissionListener
import com.salem.amna.util.Constants
import pl.tajchert.nammu.Nammu
import java.util.*

class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var mLocationPermission: LocationPermission
    private var lat: String? = null
    private var long: String? = null

    private var TAG = "AuthActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLanguage()

        Nammu.init(this)
        mLocationPermission = LocationPermission(this)
        accessLocation()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSmoothBottomMenu()
    }

    override fun initVar() {

    }

    override fun onEvent() {
    }

    override fun observeData() {
    }

    private fun setupSmoothBottomMenu() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)
    }

    private fun checkLanguage() {
        val config = resources.configuration
        //val lang = "en" // your language code
        //val lang = "ar" // your language code
        sharedPreferences = getSharedPreferences("AmnaSharedPreferences", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()
        val langCode = sharedPreferences.getString("LangCode", "en")
        assert(langCode != null)
        val locale = Locale(langCode ?: "en")
        Locale.setDefault(locale)
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun accessLocation() {
        mLocationPermission.requestPermissions(object : OnLocationPermissionListener {
            override fun onPermissionSuccess() {
                mLocationPermission.findLocation { location, _ ->
                    lat = location.latitude.toString()
                    long = location.longitude.toString()

                    if (lat.isNullOrEmpty() && location?.latitude != null) {
//                        viewLifecycleOwner.lifecycleScope.launch {
//                            localePreference.setLatLang(
//                                location.latitude.toString(),
//                                location.longitude.toString()
//                            )
//                        }
                        lat = location.latitude.toString()
                        long = location.longitude.toString()

                        Log.d(TAG, "Lat >> $lat  :  Long >> $long")

                        val map = HashMap<String, String>()
                        map[Constants.LATITUDE] = lat ?: "0"
                        map[Constants.LONGITUDE] = long ?: "0"
                    }

                    Log.e(TAG, "onPermissionSuccess: $lat $long")
                }
            }

            override fun onPermissionFailed() {
            }
        })
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mLocationPermission.onRequestPermissions(requestCode, grantResults, true)

    }

}