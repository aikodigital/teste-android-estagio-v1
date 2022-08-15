package br.com.aj.message.appaiko.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Switch
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import br.com.aj.message.appaiko.R
import br.com.aj.message.appaiko.data.MapData
import br.com.aj.message.appaiko.databinding.ActivityMapaBinding
import br.com.aj.message.appaiko.ui.adapter.SearchAdapter
import br.com.aj.message.appaiko.util.ProjectiomItemMap
import br.com.aj.message.appaiko.viewmodel.MapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MapFragment : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapaBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var projectiomItemMap : ProjectiomItemMap

    private val model by sharedViewModel<MapViewModel>()


    lateinit var smalBitmapParada: Bitmap
    lateinit var smalBitmapbus: Bitmap
    lateinit var smalBitmapacc: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMapaBinding.inflate(layoutInflater, container, false)



        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_main, menu)
                val item = menu.findItem(R.id.app_bar_switch)
                (item.actionView as Switch).setOnCheckedChangeListener { compoundButton, b ->
                    model.isThemeDark = b
                    AppCompatDelegate.setDefaultNightMode(
                        if (b) {
                            AppCompatDelegate.MODE_NIGHT_YES
                        } else {
                            AppCompatDelegate.MODE_NIGHT_NO
                        }
                    )

                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        })



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        model.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            binding.progress.visibility = View.GONE

        }

        model.updateWindow.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Atualizando ...", Toast.LENGTH_LONG).show()


        }

        model.auth.observe(viewLifecycleOwner) {

        }


        val bitparada =
            AppCompatResources.getDrawable(requireContext(), R.drawable.parada_circle)?.toBitmap()
        smalBitmapParada =
            Bitmap.createScaledBitmap(bitparada!!, bitparada.width / 2, bitparada.height / 2, false)


        val bitbus =
            AppCompatResources.getDrawable(requireContext(), R.drawable.bus_circle)?.toBitmap()
        smalBitmapbus =
            Bitmap.createScaledBitmap(bitbus!!, bitbus.width / 3, bitbus.height / 3, false)


        val accparada =
            AppCompatResources.getDrawable(requireContext(), R.drawable.acc_circle)?.toBitmap()
        smalBitmapacc  =
            Bitmap.createScaledBitmap(accparada!!, accparada.width / 2, accparada.height / 2, false)

        binding.fab.setOnClickListener {

            visibleFabs()
        }

        binding.fab2.setOnClickListener {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        binding.fab3.setOnClickListener {

           val parada =  model.calculeDistance()
           model.createRouteMap(LatLng(parada.lat.toDouble(), parada.lng.toDouble()))

        }

        model.getAuth()
    }

    fun visibleFabs() {
        if (model.visibleFabs) {
            binding.fab1.show()
            binding.fab2.show()
            binding.fab3.show()
            binding.rootContainer1.visibility = View.VISIBLE
            binding.rootContainer2.visibility = View.VISIBLE
            binding.rootContainer3.visibility = View.VISIBLE
        } else {
            binding.fab1.hide()
            binding.fab2.hide()
            binding.fab3.hide()
            binding.rootContainer1.visibility = View.GONE
            binding.rootContainer2.visibility = View.GONE
            binding.rootContainer3.visibility = View.GONE
        }
        model.visibleFabs = !model.visibleFabs
    }

    override fun onStart() {
        super.onStart()


    }


    fun permission(): Boolean {


        return !(ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED)
    }

    @SuppressLint("MissingPermission")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    )
    { permissions ->
        if (permissions.isNotEmpty()) {

            val fine = permissions[Manifest.permission.ACCESS_FINE_LOCATION]
            val couse = permissions[Manifest.permission.ACCESS_COARSE_LOCATION]

            if (requireNotNull(fine)) {

                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {



                        val mark = MarkerOptions()
                            .position(LatLng(it.latitude, it.longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(smalBitmapacc))
                        projectiomItemMap.items2.put("my_location", mark)
                        projectiomItemMap.show()
                        mMap?.animateCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
                        visibleFabs()

                    }
                }
            } else if (requireNotNull(couse)) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {

                        val mark = MarkerOptions()
                            .position(LatLng(it.latitude, it.longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(smalBitmapacc))
                        projectiomItemMap.items2.put("my_location", mark)
                        projectiomItemMap.show()
                        mMap?.animateCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
                        visibleFabs()

                    }
                }

            } else {

            }


        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(p0: GoogleMap) {

        projectiomItemMap = ProjectiomItemMap(p0)

        if (model.isThemeDark) {
            p0.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.map_dark
                )
            );
        } else {

            p0.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.map
                )
            );
        }


        binding.fab1.setOnClickListener {
            visibleFabs()
            val array = arrayListOf<SearchAdapter.Item>()
            array.addAll(model.searchAdapterItemBus)
            array.addAll(model.searchAdapterItemParada)
            if (array.size > 0) {
                ViewAlertSearch().apply {

                    mOnInputListener = object : ViewAlertSearch.Onclick {
                        override fun click(p1: Double, p2: Double) {

                            p0.animateCamera(CameraUpdateFactory.newLatLng(LatLng(p1, p2)))


                        }

                    }
                    arguments = Bundle().apply {
                        putParcelableArrayList("items", array)
                    }
                }.show(childFragmentManager, "search")

            } else {
                Snackbar.make(requireView(), "Carregando mapa...", Snackbar.LENGTH_SHORT).apply {
                    anchorView = binding.fab
                    show()

                }
            }
        }



        p0.clear()


        p0.setOnCameraMoveListener(projectiomItemMap)

        p0.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.5259153, -46.6693387), 15f))

        model.positionVehicles.observe(viewLifecycleOwner) {
            it.l?.forEach { ld ->
                ld.vs?.forEach { lv ->

                    val mark = MarkerOptions()
                        .position(LatLng(lv.py, lv.px))
                        .snippet(lv.p.toString())
                        .icon(BitmapDescriptorFactory.fromBitmap(smalBitmapbus))



                    projectiomItemMap.items2.put(lv.p.toString(), mark)

                }
            }
            projectiomItemMap.show()
            binding.progress.visibility = View.GONE
        }


        model.buscarParadasPorCorredor.observe(viewLifecycleOwner) {
            it?.forEach { lv ->


                val mark = MarkerOptions()
                    .position(LatLng(lv.py, lv.px))
                    .snippet(lv.np)
                    .icon(BitmapDescriptorFactory.fromBitmap(smalBitmapParada))
                    .title(lv.np)

                projectiomItemMap.items.put(lv.cp.toString(), mark)
            }


            projectiomItemMap.show()
            binding.progress.visibility = View.GONE
        }


        p0.setOnMapClickListener {

            if(model.visibleFabs){
                model.visibleFabs = false
                visibleFabs()
            }

            model.clickMapLocation.lat  = it.latitude
            model.clickMapLocation.lng  = it.longitude

        }
        p0.setOnMarkerClickListener {

            val m1 = (projectiomItemMap.items.filter { i -> i.key == it.tag }).size
            val m2 = (projectiomItemMap.items2.filter { i -> i.key == it.tag }).size

            if (m1 > 0) {

                (model.buscarParadasPorCorredor.value?.filter { i -> i.cp.toString() == it.tag.toString() })?.forEach { i ->
                    PrevFragment().apply {
                        arguments = Bundle().apply {
                            putString("prefixo", i.cp.toString())
                        }
                    }.show(childFragmentManager, "prev")
                }

            } else if (m2 > 0) {

            }


            mMap = p0





            true
        }

        model.routesMaps.observe(viewLifecycleOwner) {

            val lineoption = PolylineOptions()
            for (i in it.indices) {
                lineoption.addAll(it[i])
                lineoption.width(10f)
                lineoption.color(requireContext().getColor(R.color.startColor))
                lineoption.geodesic(true)
            }
            p0.addPolyline(lineoption)


        }
        // Add a marker in Sydney and move the camera
        // val sydney = LatLng(-34.0, 151.0)
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
