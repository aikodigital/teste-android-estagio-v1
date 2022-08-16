package br.com.aj.message.appaiko.viewmodel;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aj.message.appaiko.data.BuscarParadasPorCorredor
import br.com.aj.message.appaiko.data.MapData
import br.com.aj.message.appaiko.data.PositionVehicles
import br.com.aj.message.appaiko.repository.db.DatabaseRepository
import br.com.aj.message.appaiko.repository.http.HttpRepository
import br.com.aj.message.appaiko.repository.http.MapFunctionsHttpRepository
import br.com.aj.message.appaiko.ui.adapter.SearchAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.*

class MapViewModel(private val repo: HttpRepository, private val repoDatabase: DatabaseRepository,private val mapRepo: MapFunctionsHttpRepository) :
    ViewModel() {

    var visibleFabs: Boolean = true
    var isThemeDark: Boolean = false
    val clickMapLocation = MapData.LatLng()

    private var _items = MutableLiveData<PositionVehicles>()

    val positionVehicles: LiveData<PositionVehicles> = _items

    private val _auth = MutableLiveData<Boolean>()
    val auth: LiveData<Boolean> = _auth

    val error = MutableLiveData<String>()
    val buscarParadasPorCorredor = MutableLiveData<BuscarParadasPorCorredor>()
    val searchAdapterItemBus = arrayListOf<SearchAdapter.Item>()
    val searchAdapterItemParada = arrayListOf<SearchAdapter.Item>()
    val updateWindow = MutableLiveData<Int>()
    val routesMaps = MutableLiveData<ArrayList<List<LatLng>>>()



    fun getAuth() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val value = repo.getAuth()
                getCorredores()
                delay(5000)
                getBus()
                _auth.value = value
            } catch (e: Exception) {
               // error.value = e.toString()

               // ACESSANDO DATABASE
                getCorredores()
                getBus()
            }


        }

    }

    suspend fun getCorredores() {
        val array = BuscarParadasPorCorredor()

        try {
            val v = repo.corredor()
            v.forEach {
                repoDatabase.createCorredor(it)
                val c = repo.buscarParadasPorCorredor(it.cc.toString())
                c.forEach { vl -> repoDatabase.createParada(vl) }
                array.addAll(c)
            }
        } catch (e: Exception) {
            val v = repoDatabase.getAllParada()
           v?.let { array.addAll(it) }
            error.value = "Erro de Conexão"
        }

        buscarParadasPorCorredor.postValue( array)

        array.forEach {lv ->
            lv.apply {
                searchAdapterItemParada.add(SearchAdapter.Item(py,px,cp.toString(),cp.toString(),np,1))
            }
        }


    }


    fun createRouteMap(dest: LatLng){
        CoroutineScope(Dispatchers.Main).launch {
            try {
               routesMaps.value =  mapRepo.createRequestMap(
                   LatLng(clickMapLocation.lat, clickMapLocation.lng)
                   , dest)
            }catch (e:Exception){
                error.value = e.toString()
            }
        }
    }

    fun calculeDistance(): MapData.Location {
            var distance = 0.toDouble()
            var fist = MapData.Location()
        searchAdapterItemParada.forEach {
           val tmp =  SphericalUtil. computeDistanceBetween(
               LatLng(clickMapLocation.lat.toDouble(),clickMapLocation.lng.toDouble())
               ,LatLng(it.lat,it.long))



            if(distance == 0.toDouble()){
                distance = tmp
            }else{
                if(tmp < distance){
                    distance = tmp
                    fist.lat= it.lat.toString()
                    fist.lng= it.long.toString()
                }
            }
        }


        return fist
    }

     fun saveBus(){
           CoroutineScope(Dispatchers.IO).launch{
               repoDatabase.createVehicles(PositionVehicles(1,_items.value?.hr.toString()))
               _items.value?.l?.forEach {
                   it.fid = 1
                   repoDatabase.createL(it)
                   it.vs?.forEach { lv ->
                       lv.fid = it.cl
                       repoDatabase.createV(lv)

                   }
               }
           }
    }

    suspend fun getBus() {

        withContext(Dispatchers.IO) {
            try {
                val value = repo.getAllPositionVehicles()
                _items.postValue( value )
                searchAdapterItemBus.clear()
                value.id = 1
                updateWindow.postValue( 1)
                value.l?.forEach {
                    it.vs?.forEach { lv ->
                        lv.apply {

                            searchAdapterItemBus.add(
                                SearchAdapter.Item(
                                    py,
                                    px,
                                    p.toString(),
                                    p.toString(),
                                    it.c,
                                    2
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {

                val value = repoDatabase.getAllBus()
                error.postValue(e.message)
                value.let { _items.postValue(it) }
                value?.l?.forEach {
                    it.vs?.forEach { lv ->
                        lv.apply {

                            if (searchAdapterItemBus.filter { i -> i.tag == p.toString() }
                                    .isNotEmpty()) {
                                searchAdapterItemBus.find { i -> i.tag == p.toString() }?.apply {
                                    lat = py
                                    long = px
                                }
                            } else {
                                searchAdapterItemBus.add(
                                    SearchAdapter.Item(
                                        py,
                                        px,
                                        p.toString(),
                                        p.toString(),
                                        it.c,
                                        2
                                    )
                                )

                            }
                        }
                    }
                }


            }
            delay(60000)
            getBus()
        }

    }
}






