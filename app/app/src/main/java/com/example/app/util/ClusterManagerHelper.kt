package com.example.app.util

import android.content.Context
import com.example.app.ui.interfaces.ClusterRenderer
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager

/**
 * Configura um ClusterManager para gerenciar marcadores agrupados no Google Map.
 *
 * @param context Contexto atual do aplicativo.
 * @param googleMap Instância do GoogleMap onde os marcadores serão exibidos.
 * @param itemList Lista de itens que implementam a interface ClusterItem a serem exibidos como marcadores.
 * @param clusterRendererFactory Factory que cria o renderizador específico para os itens do cluster.
 * @param createInfoWindowAdapter Função lambda que cria o adaptador de janela de informações para os marcadores.
 * @return Instância configurada do ClusterManager<T> para gerenciar os marcadores.
 */
object ClusterManagerHelper {
    fun <T : ClusterItem> setupClusterManager(
        context: Context,
        googleMap: GoogleMap,
        itemList: List<T>,
        clusterRendererFactory: ClusterRenderer<T>,
        createInfoWindowAdapter: (Context) -> GoogleMap.InfoWindowAdapter
    ): ClusterManager<T> {
        val clusterManager = ClusterManager<T>(context, googleMap)

        clusterManager.clearItems()

        val renderer = clusterRendererFactory.createRenderer(context, googleMap, clusterManager)
        clusterManager.renderer = renderer

        clusterManager.markerCollection.setInfoWindowAdapter(createInfoWindowAdapter(context))

        clusterManager.addItems(itemList)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }

        return clusterManager
    }
}
