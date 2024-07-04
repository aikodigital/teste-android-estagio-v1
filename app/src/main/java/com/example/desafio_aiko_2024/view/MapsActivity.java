package com.example.desafio_aiko_2024.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.desafio_aiko_2024.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.desafio_aiko_2024.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Posiciona o mapa na cidade de São Paulo
        LatLng sp = new LatLng(-23.5475, -46.63611);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sp));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        // Exemplo de localização de um veículo
        LatLng vehiclePosition = new LatLng(-23.550520, -46.633308);
        mMap.addMarker(new MarkerOptions()
                .position(vehiclePosition)
                .title("Ônibus")
                .icon(bitmapDescriptorFromVector(this, R.mipmap.bus_position_icon_round, 0.4)));

        // Exemplo de localização de uma parada
        LatLng stopPosition = new LatLng(-23.551520, -46.633308);
        mMap.addMarker(new MarkerOptions()
                .position(stopPosition)
                .title("Parada")
                .icon(bitmapDescriptorFromVector(this, R.mipmap.bus_stop_icon_round, 0.35)));
    }

    // Redimensionamento e conversão de arquivos XML para objetos BitmapDescriptor
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId, double scaleFactor) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        int width = (int) (vectorDrawable.getIntrinsicWidth() * scaleFactor);
        int height = (int) (vectorDrawable.getIntrinsicHeight() * scaleFactor);
        vectorDrawable.setBounds(0, 0, width, height);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}