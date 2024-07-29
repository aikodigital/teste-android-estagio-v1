package com.example.bustrackingsp.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bustrackingsp.R;
import com.example.bustrackingsp.api.entities.Parada;
import com.example.bustrackingsp.mapUtils.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        if(MapUtils.getLastMapMode() == 0){
            LatLng saoPaulo = new LatLng(-23.5505, -46.6333);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 13));
        } else if (MapUtils.getLastMapMode() == 1) {
            for(LatLng pos : MapUtils.getVehiclesPosMarkers()){
                mMap.addMarker(new MarkerOptions().position(pos));
            }
        } else if (MapUtils.getLastMapMode() == 2) {
            for(Parada parada : MapUtils.getParadas()){
                mMap.addMarker(new MarkerOptions().position(new LatLng(parada.getPy(), parada.getPx())).title(parada.getEd()));
            }
        }
    }
}