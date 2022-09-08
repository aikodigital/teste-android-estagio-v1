package com.conti.onibusspemtemporeal.ui.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.data.models.BusWithLine
import com.conti.onibusspemtemporeal.databinding.FragmentMapBinding
import com.conti.onibusspemtemporeal.ui.adapter.MarkerInfoWindowAdapter
import com.conti.onibusspemtemporeal.ui.viewModel.OnibusSpViewModel
import com.conti.onibusspemtemporeal.util.BitmapHelper
import com.conti.onibusspemtemporeal.util.BusRenderer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: OnibusSpViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMap()
    }



    /** Função para observar o estado da Ui do viewModel com todos os dados necessários para atualizar a Ui*/
    private fun observerBus(
        clusterManager: ClusterManager<BusWithLine>,
        googleMap: GoogleMap,
        currentUserMarker: Marker
    ) {
        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                //Colocar o ultimo valor emitido
                viewModel.uiState.collectLatest { uiState ->

                    when {
                        //Limpar os itens que já tinham no cluster
                        //e colocar a lista com os novos itens
                        uiState.currentBuses.isNotEmpty() -> {
                            clusterManager.clearItems()
                            clusterManager.addItems(uiState.currentBuses)
                            clusterManager.cluster()
                        }

                        //Exibir na tela a mensagem do erro que ocorreu em algum operação
                        //Após mostrar a mensagem limpar as mensagem do viewmodel
                        uiState.message.isNotEmpty() -> {
                            Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG)
                                .show()
                            viewModel.clearMessages()
                        }


                    }

                    //Fazer a animação para um objeto marcado quando o usuário pedir para da zoom
                    if (uiState.zoomCurrentBuses && uiState.currentBuses.isNotEmpty() && !uiState.focusUser) {
                        val latLng = uiState.currentBuses.first().latLng

                        //mudar o zoom dependendo da quantidade  de onibus na linha
                        val zoom = if (uiState.currentBuses.size < 10) {
                            15f
                        } else {
                            10f
                        }

                        //posição que a camera vai
                        val cameraPosition = CameraPosition.Builder()
                            .target(latLng)
                            .zoom(zoom)
                            .build()

                        //chama a animação de camera
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                        //mudar o valor do zoom do onibus ao contrario do atual valor
                        viewModel.zoomBus()
                    }

                    //Função para atualizar a posição do marcador do usuário e torna ele visivel
                    if (uiState.currentLocationUser != LatLng(0.0, 0.0) && uiState.focusUser) {

                        currentUserMarker.isVisible = true
                        currentUserMarker.position = uiState.currentLocationUser

                        //posição que a camera vai

                        val cameraPosition = CameraPosition.Builder()
                            .target(uiState.currentLocationUser)
                            .zoom(18f)
                            .build()

                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                        //tirar o foco da camera do usuario
                        viewModel.offFocusUser()
                    }


                    if (uiState.isLoading) {
                        binding.progressBarLoadingBus.isVisible = true
                    } else {
                        binding.progressBarLoadingBus.isInvisible = true
                    }
                }
            }

        }
    }


    /** Função com o funcionamento completo do mapa, primeiro declaro o [mapFragment] como SupportMapFragment, para utilizar o mapa,
     *  com o mapFragment utilizo getMapAsync para ter o mapa assim que ele estiver pronto pra uso,
     *  dentro dessa lambda, na [clusterManager] instacio a classe ClusterManager com a data class [BusWithLine] que vai ter as informações da linha e de cada õnibus
     *  para utilizar no cluster, para o clusterManager é necessário um render, então criei a classe [BusRenderer] para implementar a interface da
     *  DefaultClusterRenderer e criar o render com o icon de ônibus, coloco um windowAdapter nesse cluster, para adaptar o layout da widow mostrada quando o õnibus é clicado*/
    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_fragment_container
        ) as? SupportMapFragment

        mapFragment?.getMapAsync { googleMap ->

            val clusterManager = ClusterManager<BusWithLine>(requireContext(), googleMap)

            clusterManager.renderer =
                BusRenderer(
                    requireContext(),
                    googleMap,
                    clusterManager
                )

            clusterManager.markerCollection.setInfoWindowAdapter(
                MarkerInfoWindowAdapter(
                    requireContext()
                )
            )


            //Era um teste para quando eu fizesse os itens de para, para quando clicasse em uma parada abrisse o fragment com as informações da previsão dos prox onibus
           //clusterManager.setOnClusterItemInfoWindowClickListener {
           //    Toast.makeText(
           //        requireContext(),
           //        "placard: ${it.lineCod}",
           //        Toast.LENGTH_LONG
           //    )
           //        .show()

           //}

            //Icone da localização atual do usuário
            val iconCurrentLocation: BitmapDescriptor by lazy {
                val color = ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
                BitmapHelper.vectorToBitmap(
                    requireContext(),
                    R.drawable.ic_user_location,
                    color
                )
            }

            //Marker para poder referenciar o mesmo marker e trocar a sua posição
            val currentUserMarker = googleMap.addMarker(
                MarkerOptions().position(LatLng(0.0, 0.0)).icon(iconCurrentLocation)
            )

            //o marker vai esta invisivel até o usuário querer ver a sua posição no mapa
            currentUserMarker!!.isVisible = false

            //Função para observar todas as alterações do state flow da UI STATE e assim adaptar dinamicamente a tela com as interações do usuário
            observerBus(clusterManager, googleMap, currentUserMarker)

            //Seguir a camera quando é clicado em um onibus que está no cluster
            googleMap.setOnCameraIdleListener {
                clusterManager.onCameraIdle()
            }
        }
    }

}