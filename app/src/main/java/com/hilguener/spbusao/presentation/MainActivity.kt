package com.hilguener.spbusao.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.hilguener.spbusao.R
import com.hilguener.spbusao.domain.model.Lines
import com.hilguener.spbusao.presentation.component.GoogleMapView
import com.hilguener.spbusao.presentation.component.LineItem
import com.hilguener.spbusao.presentation.component.ParadeLayout
import com.hilguener.spbusao.presentation.component.SearchBusAndStopLayout
import com.hilguener.spbusao.presentation.component.SearchLinesLayout
import com.hilguener.spbusao.presentation.map.MapsViewModel
import com.hilguener.spbusao.presentation.util.haveInternetOnInitApp
import com.hilguener.spbusao.presentation.util.networkCallback
import com.hilguener.spbusao.ui.theme.SPBusaoTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: MapsViewModel by inject()
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired: Boolean = false
    private lateinit var connectivityManager: ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeServices()

        setContent {
            val currentLocation = remember { mutableStateOf(LatLng(-23.561706, -46.655981)) }
            locationCallback = createLocationCallback(currentLocation)

            SPBusaoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MyApp(
                        currentLocation = currentLocation.value,
                    )
                }
            }
        }

        checkInternetConnection()
    }

    private fun initializeServices() {
        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {}
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private fun checkInternetConnection() {
        if (!connectivityManager.haveInternetOnInitApp()) {
            showToast("Sem conexão com a internet")
        }
    }

    private fun createLocationCallback(currentLocation: MutableState<LatLng>) =
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let {
                    currentLocation.value = LatLng(it.latitude, it.longitude)
                }
            }
        }

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyApp(
        currentLocation: LatLng,
    ) {
        val tabState = remember { mutableIntStateOf(0) }
        val bottomSheetState = rememberModalBottomSheetState()
        val isSheetOpen = rememberSaveable { mutableStateOf(false) }
        val isSheetDetailsOpen = rememberSaveable { mutableStateOf(false) }
        val selectedParadeId = rememberSaveable { mutableStateOf<String?>(null) }

        val (busLine, setBusLine) = remember { mutableStateOf("") }
        val (busStopLocation, setBusStopLocation) = remember { mutableStateOf("") }
        val (busStopLine, setBusStopLine) = remember { mutableStateOf("") }
        val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }

        val listPosVehicles by viewModel.listPosVehicles.collectAsState()
        val listParades by viewModel.listParades.collectAsState()
        val listLines by viewModel.listLines.collectAsState(initial = emptyList())
        val isListPosVehiclesEmpty by viewModel.isListPosVehiclesEmpty.collectAsState()
        val isListParadesEmpty by viewModel.isListParadesEmpty.collectAsState()

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
        }
        val uiSettings = remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
        val properties = remember {
            mutableStateOf(
                MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                        this@MainActivity,
                        R.raw.map_style,
                    ),
                ),
            )
        }

        RequestPermissionsIfNeeded()

        Scaffold(floatingActionButton = {
            FabSearch {
                isSheetOpen.value = !isSheetOpen.value
            }
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        GoogleMapView(currentLocation,
                            cameraPositionState,
                            uiSettings.value,
                            properties.value,
                            listPosVehicles,
                            listParades,
                            onMarkerClick = { id ->
                                selectedParadeId.value = id
                                isSheetDetailsOpen.value = true
                            })
                    }
                    if (isSheetOpen.value) {
                        ModalBottomSheet(
                            modifier = Modifier
                                .imePadding()
                                .fillMaxHeight(),
                            sheetState = bottomSheetState,
                            onDismissRequest = { isSheetOpen.value = false },
                        ) {
                            TabRow(
                                selectedTabIndex = tabState.intValue,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Tab(
                                    selected = tabState.intValue == 0,
                                    onClick = { tabState.intValue = 0 },
                                    text = {
                                        Text(
                                            "Paradas e Ônibus",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.displaySmall
                                        )
                                    },
                                )
                                Tab(
                                    selected = tabState.intValue == 1,
                                    onClick = { tabState.intValue = 1 },
                                    text = {
                                        Text(
                                            "Linhas",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.displaySmall
                                        )
                                    },
                                )
                            }

                            when (tabState.intValue) {
                                0 -> SearchBusAndStopLayout(
                                    busLine = busLine,
                                    onBusLineChange = setBusLine,
                                    busStopLocation = busStopLocation,
                                    onBusStopLocationChange = setBusStopLocation,
                                    busStopLine = busStopLine,
                                    onBusStopLineChange = setBusStopLine,
                                    onSearch = {
                                        if (viewModel.validateBusAndStopFields(
                                                busLine,
                                                busStopLocation,
                                                busStopLine,
                                            )
                                        ) {
                                            viewModel.getPosVehiclesByLine(busLine.toInt())
                                            viewModel.getParades(busStopLocation)
                                            isSheetOpen.value = false
                                        } else {
                                            showToast("Preencha pelo menos dois campos")
                                        }
                                    },
                                )

                                1 -> SearchLinesLayout(
                                    searchQuery = searchQuery,
                                    onSearchQueryChange = setSearchQuery,
                                    onSearch = {
                                        if (viewModel.validateSearchFields(searchQuery)) {
                                            viewModel.getLines(searchQuery)
                                        } else {
                                            showToast("Todos os campos devem ser preenchidos")
                                        }
                                    },
                                    lines = listLines,
                                )
                            }
                        }
                    }
                    if (isSheetDetailsOpen.value && selectedParadeId.value != null) {
                        val parade = viewModel.getSelectedParade(selectedParadeId.value!!)
                        ModalBottomSheet(
                            modifier = Modifier
                                .imePadding()
                                .fillMaxHeight(),
                            sheetState = bottomSheetState,
                            onDismissRequest = { isSheetDetailsOpen.value = false },
                        ) {
                            ParadeLayout(parade!!, viewModel)
                        }
                    }
                }
            }
            LaunchedEffect(isListPosVehiclesEmpty, isListParadesEmpty) {
                if (isListPosVehiclesEmpty) {
                    showToast("Nenhum veículo encontrado")
                }

                if (isListParadesEmpty) {
                    showToast("Nenhuma parada encontrada")
                }
            }
        }
    }


    @Composable
    private fun RequestPermissionsIfNeeded() {
        val launchPermissionRequest =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val allGranted = permissions.values.all { it }
                locationRequired = allGranted
                if (allGranted) startLocationUpdates()
            }

        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    this, it,
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocationUpdates()
        } else {
            LaunchedEffect(launchPermissionRequest) {
                launchPermissionRequest.launch(permissions)
            }
        }

        connectivityManager.networkCallback(
            ::callbackOnAvailable,
            ::callbackOnLost,
        )
    }

    @Composable
    fun FabSearch(onClick: () -> Unit) {
        FloatingActionButton(onClick = onClick) {
            Icon(Icons.Default.Search, contentDescription = "Search")
        }
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper(),
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun callbackOnAvailable() {
        lifecycleScope.launch {
            viewModel.authenticate(this@MainActivity)
        }
    }

    private fun callbackOnLost() {
        lifecycleScope.launch {
            showToast(resources.getString(R.string.txt_no_conection))
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewLineItem() {
        val line = Lines(
            codeOfLine = 123,
            signOfLineDirectionPrincipal = "Principal",
            signOfLineDirectionSecondary = "Secundário",
            worksInCircleMode = true,
            directionOfWorks = 1,
            firstPartOfSignLine = "",
            secondPartOfSignLine = 5,
        )
        LineItem(line = line)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.N)
    @Preview
    @Composable
    fun MyAppPreview() {
        SPBusaoTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                val location = LatLng(-23.561706, -46.655981)
                MyApp(
                    location
                )
            }
        }
    }

}
