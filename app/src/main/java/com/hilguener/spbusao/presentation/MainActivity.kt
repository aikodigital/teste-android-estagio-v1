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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hilguener.spbusao.R
import com.hilguener.spbusao.domain.model.Lines
import com.hilguener.spbusao.domain.model.Parades
import com.hilguener.spbusao.domain.model.Vehicles
import com.hilguener.spbusao.presentation.map.MapsViewModel
import com.hilguener.spbusao.presentation.util.haveInternetOnInitApp
import com.hilguener.spbusao.presentation.util.networkCallback
import com.hilguener.spbusao.ui.theme.SPBusaoTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: MapsViewModel by inject()
    private val permissions =
        arrayOf(
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
        initializeViewModel()

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
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MyApp(
        currentLocation: LatLng,
    ) {
        val tabState = remember { mutableIntStateOf(0) }
        val bottomSheetState = rememberModalBottomSheetState()
        val isSheetOpen = rememberSaveable { mutableStateOf(false) }
        val cameraPositionState =
            rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
            }
        val uiSettings = remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
        val properties =
            remember {
                mutableStateOf(
                    MapProperties(
                        mapStyleOptions =
                            MapStyleOptions.loadRawResourceStyle(
                                this@MainActivity,
                                R.raw.map_style,
                            ),
                    ),
                )
            }

        val (busLine, setBusLine) = remember { mutableStateOf("") }
        val (busStopLocation, setBusStopLocation) = remember { mutableStateOf("") }
        val (busStopLine, setBusStopLine) = remember { mutableStateOf("") }
        val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }

        val listPosVehicles by viewModel.listPosVehicles.collectAsState()
        val listParades by viewModel.listParades.collectAsState()
        val listLines by viewModel.listLines.collectAsState(initial = emptyList())

        RequestPermissionsIfNeeded()

        Scaffold(floatingActionButton = {
            FabSearch {
                isSheetOpen.value = !isSheetOpen.value
            }
        }) { paddingValues ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                    ) {
                        GoogleMapView(
                            currentLocation,
                            cameraPositionState,
                            uiSettings.value,
                            properties.value,
                            listPosVehicles,
                            listParades,
                        )
                    }

                    if (isSheetOpen.value) {
                        ModalBottomSheet(
                            modifier =
                                Modifier
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
                                    text = { Text("Paradas e Ônibus") },
                                )
                                Tab(
                                    selected = tabState.intValue == 1,
                                    onClick = { tabState.intValue = 1 },
                                    text = { Text("Linhas") },
                                )
                            }

                            when (tabState.intValue) {
                                0 ->
                                    SearchBusAndStopLayout(
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
                                            } else {
                                                showToast("Preencha pelo menos dois campos")
                                            }
                                        },
                                    )

                                1 ->
                                    SearchLinesLayout(
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
                }
            }
        }
    }

    @Composable
    fun GoogleMapView(
        currentLocation: LatLng,
        cameraPositionState: CameraPositionState,
        uiSettings: MapUiSettings,
        properties: MapProperties,
        listPosVehicles: List<Vehicles>,
        listParades: List<Parades>,
    ) {
        val currentState = MarkerState(currentLocation)
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
        ) {
            listPosVehicles.forEach { posVehicle ->
                Marker(
                    state = MarkerState(LatLng(posVehicle.latitude, posVehicle.longitude)),
                    title = "Veículo ${posVehicle.prefixOfVehicle}",
                )
            }

            listParades.forEach { parade ->
                Marker(
                    state = MarkerState(LatLng(parade.latitude, parade.longitude)),
                    title = "Parada ${parade.nameOfParade}",
                )
            }

            Marker(state = currentState, title = "Você está aqui")
        }

        LaunchedEffect(currentLocation) {
            if (currentLocation != LatLng(0.0, 0.0)) {
                cameraPositionState.animate(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(
                            currentLocation,
                            15f,
                        ),
                    ),
                    500,
                )
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
                showToast(if (allGranted) "Permissões concedidas" else "Permissões negadas")
            }

        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    this, it,
                ) == PackageManager.PERMISSION_GRANTED
            }
        ) {
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

    @Composable
    fun SearchBusAndStopLayout(
        busLine: String,
        onBusLineChange: (String) -> Unit,
        busStopLocation: String,
        onBusStopLocationChange: (String) -> Unit,
        busStopLine: String,
        onBusStopLineChange: (String) -> Unit,
        onSearch: () -> Unit,
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = busLine,
                onValueChange = onBusLineChange,
                label = { Text("Linha") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = busStopLocation,
                onValueChange = onBusStopLocationChange,
                label = { Text("Parada") },
                modifier = Modifier.fillMaxWidth(),
                enabled = busStopLine.isEmpty(),
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                keyboardActions =
                    KeyboardActions(onSearch = {
                        keyboardController?.hide()
                        onSearch()
                    }),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = busStopLine,
                onValueChange = onBusStopLineChange,
                label = { Text("Linha da Parada") },
                modifier = Modifier.fillMaxWidth(),
                enabled = busStopLocation.isEmpty(),
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Search,
                    ),
                keyboardActions =
                    KeyboardActions(onSearch = {
                        keyboardController?.hide()
                        onSearch()
                    }),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    @Composable
    fun SearchLinesLayout(
        searchQuery: String,
        onSearchQueryChange: (String) -> Unit,
        onSearch: () -> Unit,
        lines: List<Lines>?,
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Column {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Nome da Linha") },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                keyboardActions =
                    KeyboardActions(onSearch = {
                        keyboardController?.hide()
                        onSearch()
                    }),
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(lines ?: emptyList()) { line ->
                    LineItem(line = line)
                }
            }
        }
    }

    @Composable
    fun LineItem(line: Lines) {
        ElevatedCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
        ) {
            Column {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = line.codeOfLine.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 20.dp),
                        style = MaterialTheme.typography.displaySmall,
                    )
                    Text(
                        text = line.signOfLineDirectionPrincipal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_alt_24),
                        contentDescription = "Arrow",
                        modifier = Modifier.padding(8.dp),
                    )
                    Text(
                        text = line.signOfLineDirectionSecondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Circular? ${if (line.worksInCircleMode) "Sim" else "Não"}",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Sentido: ${if (line.directionOfWorks == 1) "Principal para secundário" else "Secundário para principal"}",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text =
                        stringResource(
                            R.string.txt_sign,
                            line.firstPartOfSignLine,
                            line.secondPartOfSignLine.toString(),
                        ),
                    modifier = Modifier.padding(bottom = 16.dp, start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest =
            LocationRequest.create().apply {
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

    private fun initializeViewModel() {
        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let { showToast(it) }
            }
        }
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
        val line =
            Lines(
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
}
