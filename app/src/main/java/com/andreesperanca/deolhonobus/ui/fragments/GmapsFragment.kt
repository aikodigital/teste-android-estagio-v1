package com.andreesperanca.deolhonobus.ui.fragments

import androidx.fragment.app.Fragment


class GmapsFragment : Fragment() {

//    private val binding by lazy {
//        FragmentGmapsBinding.inflate(layoutInflater)
//    }
//
//    private val args: GmapsFragmentArgs by navArgs()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View = binding.root
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
//
//        mapFragment.getMapAsync { googleMap ->
//            addMarkers(googleMap)
//            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), raw.map_style))
//            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(requireContext()))
//            googleMap.setOnMapLoadedCallback {
//                val bounds = LatLngBounds.Builder()
//                args.markeringmaps?.listMarker?.forEach {
//                    bounds.include(it.lng)
//                    googleMap.moveCamera(
//                        CameraUpdateFactory.newLatLngBounds(
//                            bounds.build(),
//                            80
//                        )
//                    )
//                }
//            }
//        }
//    }
//
//    private fun addMarkers(googleMap: GoogleMap) {
//        args.markeringmaps?.listMarker?.forEach {
//            val marker1 = googleMap.addMarker(
//                MarkerOptions()
//                    .title(args.markeringmaps?.title)
//                    .position(it.lng)
//                    .icon(
//                        BitMapHelper.vectorToBitMap(
//                            requireContext(),
//                            drawable.bus_stop,
//                            ContextCompat.getColor(requireContext(), color.dark_blue)
//                        )
//                    )
//            )
//            marker1?.tag = it
//        }
//    }
}


