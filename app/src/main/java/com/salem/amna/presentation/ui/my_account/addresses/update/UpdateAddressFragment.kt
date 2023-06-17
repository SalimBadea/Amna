package com.salem.amna.presentation.ui.my_account.addresses.update


import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.data.models.common.Cities
import com.salem.amna.data.models.common.Governorates
import com.salem.amna.databinding.FragmentAddNewAddressBottomSheetBinding
import com.salem.amna.databinding.FragmentUpdateAddressBinding
import com.salem.amna.presentation.common.AppSharedViewModel
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.my_account.addresses.add.AddAddressEvent
import com.salem.amna.util.getBitmapDescriptorFromVector
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UpdateAddressFragment : BaseFragment() {
    private val TAG = "PickLocationFragment"

    private lateinit var addNewAddressBinding: FragmentAddNewAddressBottomSheetBinding
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 45
    private val viewModel: UpdateAddressViewModel by viewModels()
    private val sharedViewModel: AppSharedViewModel by activityViewModels()
    private var lat: String? = null
    private var long: String? = null
    private var areaId: Int? = null
    private var cityId: Int? = null
    private lateinit var areaList: List<Governorates>
    private var cityList: List<Cities>? = null
    private val binding by lazy {
        FragmentUpdateAddressBinding.inflate(layoutInflater)
    }
    private lateinit var currentUserLocation: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getRootView(): View {
        addNewAddressBinding = binding.addressNewAddressContainer
        return binding.root
    }


    override fun initVar() {
        lifecycleScope.launchWhenStarted {
            sharedViewModel.address.collect { address ->
                initMap(address)
                initUpdateAddressSheet(address)
                areaId = address?.governorate?.id
                cityId = address?.city?.id
                Log.e(TAG, "Updated Address >> $address")
                address?.let {
                    viewModel.onEvent(AddAddressEvent.InitState(address))
                }
            }
        }
    }

    private fun initMap(address: AddressModel?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        lifecycle.coroutineScope.launchWhenCreated {
            val googleMap = mapFragment?.awaitMap()

            googleMap?.awaitMapLoad()
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = true
//            googleMap?.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                    requireContext(), R.raw.map_style
//                )
//            );
            if (lat != null) {
                currentUserLocation = LatLng(lat!!.toDouble(), long!!.toDouble())
            } else {

                currentUserLocation =
                    LatLng(address?.lat ?: 24.774265, address?.lng ?: 46.738586)


                val cameraFromPosition = CameraPosition.Builder()
                    .target(currentUserLocation)
                    .zoom(14f)
                    .build()
                googleMap?.animateCamera(
                    CameraUpdateFactory.newCameraPosition(cameraFromPosition),
                    2000,
                    null
                )
                val marker = googleMap?.addMarker {
                    position(currentUserLocation)
                    title(address?.address ?: "Saudi Arabia")
                    draggable(true)
                    icon(
                        getBitmapDescriptorFromVector(
                            requireContext(),
                            R.drawable.ic_location
                        )
                    )
                }
                getAddressInfo(currentUserLocation.latitude, currentUserLocation.longitude)
                googleMap?.setOnCameraIdleListener {

                    //currentUserLocation = map.cameraPosition.target
                    val addressInfo = getAddressInfo(
                        marker?.position?.latitude ?: 24.774265,
                        marker?.position?.longitude ?: 46.738586
                    )
                    marker?.title = addressInfo
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


                googleMap?.setOnCameraMoveListener {
                    currentUserLocation = googleMap.cameraPosition.target
                    marker?.position = currentUserLocation
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
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
                    baseActivity.onBackPressed()
                    hideLoadingDialog()
                } else if (state.isSuccessful) {
                    if (state.resultAreas?.governorates != null)
                        areaList = state.resultAreas.governorates

                    Log.e(TAG, " AreaList >> $areaList")

                    cityList = state.resultCities?.cities
                    intiAreasSpinner(
                        state.resultAreas?.governorates,
                        state.resultCities?.cities,
                        state.areaId
                    )
                    intiCities(state.resultCities?.cities, state.cityId)
                    hideLoadingDialog()
                    Log.d(TAG, "render: isSuccess")
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
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
                    Log.e(TAG, "Selection >> $selection")
                    if (it != 0 && type?.id == selection) {
                        selectedArea = type.index ?: -1
                        Log.e(TAG, "SelectedArea >> $selectedArea")
                        Log.e(TAG, "Selection >> $selection")
                        addNewAddressBinding.areaField.setText(type.name?.ar, false)
                        Log.e(TAG, "SelectArea >> ${type.name?.ar}")
                    }
                }
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_area_type, areas)
            addNewAddressBinding.areaField.setAdapter(adapter)

            if (selectedArea != -1) {
                val select = list[selectedArea]
                Log.e(TAG, "Select >> $select")
                Log.e(TAG, "Area ID >> $areaId")
                select?.let {
                    viewModel.onEvent(AddAddressEvent.AreaChanged(select.id ?: 0, cities == null))
                    addNewAddressBinding.areaField.setText(select.name?.ar, false)
                    Log.e(TAG, "Select1 >> $select")
                }
            }

            addNewAddressBinding.areaField.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d(TAG, "intiCitiesSpinner: $position")
                    viewModel.onEvent(AddAddressEvent.AreaChanged(list[position]?.id ?: 0, true))

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

                        Log.e(TAG, "selectedCity >> $selectedCity")

                        Log.e(TAG, "selection >> $selection")

                        Log.e(TAG, "typeIndex >> ${type.index}")

                    }
                }
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_area_type, cities)
            addNewAddressBinding.cityField.setAdapter(adapter)
            if (selectedCity != -1) {
                val select = list[selectedCity]
                Log.e(TAG, "select >> $select")
                select?.let {
                    viewModel.onEvent(AddAddressEvent.CityChanged(select.id ?: 0))
                    addNewAddressBinding.cityField.setText(select.name?.ar, false)
                    Log.e(TAG, "select1 >> $select")
                }
                addNewAddressBinding.cityField.setText(select?.name?.ar, false)
                Log.e(TAG, "selectCity >> ${select?.name?.ar}")
            }
            addNewAddressBinding.cityField.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d(TAG, "intiCitiesSpinner: $position")
                    viewModel.onEvent(AddAddressEvent.CityChanged(list[position]?.id ?: 0))

                }
        }
    }

    private fun handelMarkDraggable(googleMap: GoogleMap?) {
        googleMap?.setOnMarkerDragListener(object : OnMarkerDragListener {
            override fun onMarkerDrag(marker: Marker) {
                Log.d(
                    TAG,
                    "onMarkerDrag: lat:${marker.position.latitude}   long:${marker.position.longitude}"
                )
                marker.title = getAddressInfo(marker.position.latitude, marker.position.longitude)


            }

            override fun onMarkerDragEnd(marker: Marker) {
                Log.d(
                    TAG,
                    "onMarkerDrag: lat:${marker.position.latitude}   long:${marker.position.longitude}"
                )
                marker.title = getAddressInfo(marker.position.latitude, marker.position.longitude)

            }

            override fun onMarkerDragStart(marker: Marker) {
                Log.d(
                    TAG,
                    "onMarkerDrag: lat:${marker.position.latitude}   long:${marker.position.longitude}"
                )
                marker.title = getAddressInfo(marker.position.latitude, marker.position.longitude)

            }

        })
    }

    private fun initUpdateAddressSheet(address: AddressModel?) {
        if (::areaList.isInitialized) {
            Log.e(TAG, "AreaList Size >> ${areaList.size}")
            areaList.forEach { area ->
                Log.e(TAG, "Area Id >> ${area.id}")
                Log.e(TAG, "Area >> $area")
                if (areaId == area.id) {
                    addNewAddressBinding.areaField.setText(area.name?.ar, false)
                    Log.e(TAG, "Select1 >> $area")
                }
            }
        }
        addNewAddressBinding.address.text = address?.address
        addNewAddressBinding.areaField.setText(address?.governorate?.name!!)
        addNewAddressBinding.cityField.setText(address.city?.name!!)

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
}