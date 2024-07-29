package me.patrick.aikodigital.pontocerto.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import me.patrick.aikodigital.pontocerto.R;
import me.patrick.aikodigital.pontocerto.controller.GoogleMapController;

public class GMapView extends Fragment implements OnMapReadyCallback {

    private GoogleMapController googleMapController;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gmap_view, container, false);
        setupGMap();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        initGMap(googleMap);
        googleMapController = new GoogleMapController(requireView(), requireContext(), googleMap);
        googleMapController.fetchBusStopPositions();

    }

    private void setupGMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.id_map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    private void initGMap(@NonNull GoogleMap googleMap) {
        LatLng saoPaulo = new LatLng(-23.55052, -46.633308);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(saoPaulo, 13);
        googleMap.moveCamera(cameraUpdate);

    }














}
