package com.example.transportesp.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.transportesp.data.BusStop
import com.example.transportesp.R
import com.example.transportesp.data.Vehicle
import com.example.transportesp.data.VehicleClusterItem
import com.example.transportesp.databinding.FragmentHomeBinding
import com.example.transportesp.http.OlhoVivoApi
import com.example.transportesp.http.RetrofitInstance
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var apiService: OlhoVivoApi
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var clusterManager: ClusterManager<VehicleClusterItem>? = null
    private var markerCount = 0 // contador de marcadores adicionados ao mapa
    private val token = "2c6db6c4a0a1eef270b335bfe67e134326a507615ff86d699e6333afa16d85cf"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = RetrofitInstance.api

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val saoPaulo = LatLng(-23.550520, -46.633308)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 13.0f))

        setupClusterManager()

        token.autenticarComToken()
    }

    private fun setupClusterManager() {
        clusterManager = ClusterManager(context, mMap)
        clusterManager?.renderer = CustomClusterRenderer(requireContext(), mMap, clusterManager!!)
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
    }
// Autenticar com o token
    private fun String.autenticarComToken() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val autenticado = apiService.autenticar("Bearer $this")
                if (autenticado) {
                    buscarPosicoesVeiculos("Bearer $this")
                } else {
                    showError("Falha na autenticação")
                }
            } catch (e: Exception) {
                showError("Erro na autenticação: ${e.message}")
            }
        }
    }

    private fun buscarPosicoesVeiculos(token: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val posicoesVeiculos = apiService.getPosicoesVeiculos(token)
                val veiculosSP = posicoesVeiculos.l.flatMap { it.vs }
                    .filter { isWithinSaoPaulo(it.py, it.px) }

                withContext(Dispatchers.Main) {
                    addVeiculosToMap(veiculosSP)
                    buscarParadas(token)
                }
            } catch (e: Exception) {
                showError("Erro ao buscar posições dos veículos: ${e.message}")
            }
        }
    }

    /* Aki basicamente verifica se o ponto esta dentro da cidade de São Paulo, para não pegar
    de todo o estado*/
    private fun isWithinSaoPaulo(latitude: Double, longitude: Double): Boolean {
        val minLat = -23.681531
        val maxLat = -23.473774
        val minLng = -46.735583
        val maxLng = -46.458093
        return latitude in minLat..maxLat && longitude in minLng..maxLng
    }

    private fun addVeiculosToMap(veiculos: List<Vehicle>) {
        clusterManager?.clearItems()
        markerCount = 0

        for (veiculo in veiculos) {
            val position = LatLng(veiculo.py, veiculo.px)
            val clusterItem = VehicleClusterItem(position, "Veículo ${veiculo.p}")
            clusterManager?.addItem(clusterItem)
            markerCount++
        }

        clusterManager?.cluster()
        Log.d("HomeFragment", "Número total de marcadores adicionados: $markerCount")
    }

    private fun buscarParadas(token: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val paradas = apiService.buscarParadas(token, "")
                val paradasSP = paradas.filter { isWithinSaoPaulo(it.py, it.px) }

                withContext(Dispatchers.Main) {
                    addParadasToMap(paradasSP)
                }
            } catch (e: Exception) {
                showError("Erro ao buscar paradas: ${e.message}")
            }
        }
    }

    private fun addParadasToMap(paradas: List<BusStop>) {
        val paradaIcon = 48.resizeBitmap(R.drawable.ic_bus_parada, 48)

        for (parada in paradas) {
            val position = LatLng(parada.py, parada.px)
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(parada.np)
                    .snippet(parada.ed)
                    .icon(paradaIcon)
            )
            marker?.tag = parada.cp
        }

        // Quando clicar em uma parada, buscar as previsões de chegada e mostrar em um dialog

        mMap.setOnMarkerClickListener { marker ->
            val codigoParada = marker.tag as? Int
            if (codigoParada != null) {
                buscarPrevisoesChegada("Bearer $token", codigoParada)
            }
            false
        }

        Log.d("HomeFragment", "Número total de paradas adicionadas: ${paradas.size}")
    }

    private fun buscarPrevisoesChegada(token: String, codigoParada: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val responseWrapper = apiService.buscarPrevisoesChegada(token, codigoParada)
                val previsoes = mutableListOf<ArrivalPrediction>()

                val predictionResponse = responseWrapper.p
                for (linePrediction in predictionResponse.l) {
                    for (vehiclePrediction in linePrediction.vs) {
                        val arrivalPrediction = ArrivalPrediction(
                            lt0 = linePrediction.lt0,
                            p = vehiclePrediction.p,
                            t = vehiclePrediction.t
                        )
                        previsoes.add(arrivalPrediction)
                    }
                }

                withContext(Dispatchers.Main) {
                    mostrarPrevisoesChegada(previsoes)
                }
            } catch (e: Exception) {
                showError("Erro ao buscar previsões de chegada: ${e.message}")
            }
        }
    }

    private fun mostrarPrevisoesChegada(previsoes: List<ArrivalPrediction>) {
        val mensagem = StringBuilder()
        for (previsao in previsoes) {
            mensagem.append("Linha: ${previsao.lt0}\n")
            mensagem.append("Veículo: ${previsao.p}\n")
            mensagem.append("Horário de chegada: ${previsao.t}\n\n")
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Previsões de Chegada")
            .setMessage(mensagem.toString())
            .setPositiveButton("Sair") { dialog, _ -> dialog.dismiss() }
            .create()
            .apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(resources.getColor(android.R.color.black))
                }
                show()
            }
    }

    private fun showError(message: String) {

        Log.e("HomeFragment", message)
    }

    private fun Int.resizeBitmap(drawableRes: Int, height: Int): BitmapDescriptor {
        val imageBitmap = BitmapFactory.decodeResource(resources, drawableRes)
        val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, this, height, false)
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
    }

    private class CustomClusterRenderer(
        context: Context,
        map: GoogleMap,
        clusterManager: ClusterManager<VehicleClusterItem>
    ) : DefaultClusterRenderer<VehicleClusterItem>(context, map, clusterManager) {

        private val vehicleIcon: BitmapDescriptor

        init {
            val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_bus)
            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 48, 48, false)
            vehicleIcon = BitmapDescriptorFactory.fromBitmap(resizedBitmap)
        }


        override fun onBeforeClusterItemRendered(item: VehicleClusterItem, markerOptions: MarkerOptions) {
            markerOptions.icon(vehicleIcon).title(item.title) // adicionar icone para todos os marcadores do cluster
        }

        override fun onBeforeClusterRendered(cluster: Cluster<VehicleClusterItem>, markerOptions: MarkerOptions) {
            markerOptions.icon(vehicleIcon) // clusterear todos os marcadores do cluster
        }
    }
}
