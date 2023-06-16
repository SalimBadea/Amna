package com.salem.amna.presentation.ui.my_account.addresses.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import com.salem.amna.R
import com.salem.amna.base.BaseActivity
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.Cities
import com.salem.amna.data.models.common.CitiesModel
import com.salem.amna.data.models.common.Governorates
import com.salem.amna.data.models.common.GovernoratesModel
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.FragmentAddAddressBinding
import com.salem.amna.databinding.FragmentAddNewAddressBottomSheetBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Constants
import com.salem.amna.util.getBitmapDescriptorFromVector
import com.salem.amna.util.hideView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddAddressFragment : BaseFragment() {
    private val TAG = "AddAddressFragment"

    private lateinit var addNewAddressBinding: FragmentAddNewAddressBottomSheetBinding
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 45
    private var lat: String? = null
    private var long: String? = null
    private var userLatLong: LatLng? = null
    private var googleMap: GoogleMap? = null

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    @Inject
    lateinit var localePreference: LocalePreference
    private val viewModel: AddAddressViewModel by viewModels()
    private val binding by lazy {
        FragmentAddAddressBinding.inflate(layoutInflater)
    }
    private lateinit var currentUserLocation: LatLng

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.hideView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.hideView()
        addNewAddressBinding = binding.addressNewAddressContainer
        return binding.root
    }


    override fun initVar() {
        checkLocationPermission()
        if (checkAndRequestPermissions())
            initMap()
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        lifecycle.coroutineScope.launchWhenCreated {
            val googleMap = mapFragment?.awaitMap()
            googleMap?.let { map ->
                googleMap.awaitMapLoad()
                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true
//            googleMap?.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                    requireContext(), R.raw.map_style
//                )
//            )
//            val saudiArabia = LatLng(24.774265, 46.738586)

                if (lat != null) {
                    userLatLong = LatLng(lat!!.toDouble(), long!!.toDouble())

                    val cameraFromPosition = CameraPosition.Builder()
                        .target(userLatLong!!)
                        .zoom(14f)
                        .build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(cameraFromPosition),
                        2000,
                        null
                    )
                    val marker = googleMap.addMarker {
                        position(userLatLong!!)
                        title("Saudi Arabia")
                        draggable(true)
                        icon(
                            getBitmapDescriptorFromVector(
                                requireContext(),
                                R.drawable.ic_location
                            )
                        )
                    }
                    getAddressInfo(lat!!.toDouble(), long!!.toDouble())
//                getAddressInfo(24.774265, 46.738586)

                    //handelMarkDraggable(googleMap)
                    googleMap.setOnCameraIdleListener {

                        //currentUserLocation = map.cameraPosition.target
                        val address = getAddressInfo(
                            marker.position.latitude ?: 24.774265,
                            marker.position.longitude ?: 46.738586
                        )
                        marker.title = address
                        Log.d(
                            TAG,
                            "initMap: ${currentUserLocation.latitude}, ${currentUserLocation.longitude}"
                        )
                        currentUserLocation = googleMap.cameraPosition.target
                        Log.d(
                            TAG,
                            "initMap: ${currentUserLocation.latitude}, ${currentUserLocation.longitude}"
                        )
                        //marker.position = currentUserLocation
                    }

                    googleMap.setOnCameraMoveListener {
                        currentUserLocation = googleMap.cameraPosition.target
                        marker.position = currentUserLocation
                    }
                }
            }
        }
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener { baseActivity.onBackPressed()}
        addNewAddressBinding.addButton.setOnClickListener {
            viewModel.onEvent(AddAddressEvent.SaveAddress)
        }
    }

    override fun render() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.isSuccess) {
                        hideLoadingDialog()
                        baseActivity.onBackPressed()
                        Log.d(TAG, "render: isSuccess")
                    }else if (state.isSuccessful) {
                        intiAreasSpinner(state.resultAreas?.governorates,state.resultCities?.cities, state.areaId)
                        intiCities(state.resultCities?.cities, state.cityId)
                        hideLoadingDialog()
                        Log.d(TAG, "render: isSuccessful")
                    }  else if (state.isLoading) {
                        showLoadingDialog()
                        Log.d(TAG, "render: isLoading")

                    } else if (state.error.isNotBlank()) {
                        hideLoadingDialog()
                        Log.d(TAG, "render: error")
                    } else {
                        hideLoadingDialog()
                    }
                    Log.d(TAG, "render: none ${state.error}")
                }
            }
        }
    }

    override fun navigate() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigation.collect { navigation ->
                when (navigation) {
                    NavigationCommand.Back -> {
                        baseActivity.onBackPressed()
                    }
                    is NavigationCommand.ToDirection -> {
                        findNavController().navigate(navigation.directions)
                    }
                    else -> {}
                }

            }
        }
    }

    override fun showEffect() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is UiEffect.ShowToast -> {
                        Log.e(TAG, "showEffect: ${effect.message}")
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
    }

    private fun intiAreasSpinner(list: List<Governorates?>?, cities: List<Cities?>?, selection: Int?) {
        val areas = mutableListOf<String>()
        if (list != null) {
            var selectedArea = -1
            for (type in list) {
                areas.add(type?.name?.ar ?: "")
                selection?.let {
                    if (it != 0 && type?.id == selection) {
                        selectedArea = type.index ?: -1
                    }
                }
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_area_type, areas)
            addNewAddressBinding.areaField.setAdapter(adapter)
            if (selectedArea != -1 ) {
                val select = list[selectedArea]
                select?.let {
                    viewModel.onEvent(AddAddressEvent.AreaChanged(select.id?:0,cities == null))
                    addNewAddressBinding.areaField.setText(select.name?.ar, false)
                }
            }

            addNewAddressBinding.areaField.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d(TAG, "intiCitiesSpinner: $position")
                    viewModel.onEvent(AddAddressEvent.AreaChanged(list[position]?.id ?: 0,true))

                }
        }
    }

    private fun intiCities(list: List<Cities?>?, selection: Int?) {
        val cities = mutableListOf<String>()
        if (list != null) {
            var selectedCity = -1
            for (type in list) {
                cities.add(type?.name?.ar ?: "")
                selection?.let {
                    if (it != 0 && type?.id == selection) {
                        selectedCity = type.index ?: -1
                    }
                }
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_area_type, cities)
            addNewAddressBinding.cityField.setAdapter(adapter)
            if (selectedCity != -1 ) {
                val select = list[selectedCity]
                select?.let {
                    viewModel.onEvent(AddAddressEvent.CityChanged(select.id?:0))
                    addNewAddressBinding.cityField.setText(select.name?.ar, false)
                }
            }
            addNewAddressBinding.cityField.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d(TAG, "intiCitiesSpinner: $position")
                    viewModel.onEvent(AddAddressEvent.CityChanged(list[position]?.id ?: 0))

                }
        }
    }

    private fun getColor(color: Int) = ContextCompat.getColorStateList(
        requireContext(),
        color
    )

    private fun getAddressInfo(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val geoAddresses = geocoder.getFromLocation(latitude, longitude, 1)
        geoAddresses?.let { addresses ->
            val address = addresses[0].getAddressLine(0)
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName
            val adminArea = addresses[0].adminArea
            val subAdminArea = addresses[0].subAdminArea
            Log.d(TAG, "getAddressInfo: address:$address")
            Log.d(TAG, "getAddressInfo: city:$city")
            Log.d(TAG, "getAddressInfo: state:$state")
            Log.d(TAG, "getAddressInfo: country:$country")
            Log.d(TAG, "getAddressInfo: knownName:$knownName")
            Log.d(TAG, "getAddressInfo: adminArea:$adminArea")
            Log.d(TAG, "getAddressInfo: subAdminArea:$subAdminArea")
            addNewAddressBinding.address.text = addresses[0].getAddressLine(0)
            val fullAddress = "$country, $state, $city, $knownName"
            viewModel.onEvent(AddAddressEvent.LatChanged(latitude.toString()))
            viewModel.onEvent(AddAddressEvent.LongChanged(longitude.toString()))
            viewModel.onEvent(AddAddressEvent.FullAddressChanged(fullAddress))
            return fullAddress
        }
        return ""
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            val location = it.result //obtain location
            if (location != null) {

                val latLng = LatLng(location.latitude, location.longitude)
                // create a marker at the exact location
                googleMap?.addMarker(
                    MarkerOptions().position(latLng)
                        .title("you_are_currently_here")
                )
                // create an object that will specify how the camera will be updated
                val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)

                googleMap?.moveCamera(update)
                //Save the location data to the database
            } else {
                // if location is null , log an error message
                Log.e(TAG, "No location found")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun handelAcceptedPermission() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Log.d(
                    TAG,
                    "handelAcceptedPermission: latitude=${location?.latitude}, longitude=${location?.longitude}"
                )
                if (lat.isNullOrEmpty() && location?.latitude != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        localePreference.setLatLang(
                            location.latitude.toString(),
                            location.longitude.toString()
                        )
                    }
                    lat = location.latitude.toString()
                    long = location.longitude.toString()

                    val map = HashMap<String, String>()
                    map[Constants.LATITUDE] = lat ?: "0"
                    map[Constants.LONGITUDE] = long ?: "0"
                    hideLoadingDialog()
                }
            }
        getCurrentLocation()
    }

    /** ========================================================== Permissions ==================================================== */
    private fun checkAndRequestPermissions(): Boolean {
        val locationPermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        val coarsePermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (coarsePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                && ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        Log.d(TAG, "checkLocationPermission: explained")
                        locationPermissionRequest.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                    .create()
                    .show()
            } else {
                Log.d(TAG, "checkLocationPermission: no need explained")

                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    /**====================================== Get Location ==========================================*/
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    handelAcceptedPermission()
                    Log.d(TAG, "locationPermissionRequest:  Precise location access granted.")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    Log.d(
                        TAG,
                        "locationPermissionRequest: Only approximate location access granted."
                    )
                    handelAcceptedPermission()
                }
                else -> {
                    // No location access granted.
                    Log.d(TAG, "locationPermissionRequest: No location access granted.")
                }
            }
        }
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
}