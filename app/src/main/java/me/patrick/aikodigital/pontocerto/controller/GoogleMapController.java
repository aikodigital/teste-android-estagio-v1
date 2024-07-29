package me.patrick.aikodigital.pontocerto.controller;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Locale;
import java.util.Map;


import me.patrick.aikodigital.pontocerto.R;
import me.patrick.aikodigital.pontocerto.adapter.BusStopAdapter;
import me.patrick.aikodigital.pontocerto.adapter.SearchAdapter;
import me.patrick.aikodigital.pontocerto.model.BusStopPositionModel;
import me.patrick.aikodigital.pontocerto.model.BusVehicleModel;
import me.patrick.aikodigital.pontocerto.model.EstimatedBusModel;
import me.patrick.aikodigital.pontocerto.model.response.BusPositionResponse;
import me.patrick.aikodigital.pontocerto.model.response.BusStopResponse;
import me.patrick.aikodigital.pontocerto.network.ServiceApi;
import me.patrick.aikodigital.pontocerto.network.ServiceProvider;
import me.patrick.aikodigital.pontocerto.util.DrawableUtils;
import retrofit2.Call;
import retrofit2.Response;

public class GoogleMapController {

    private final GoogleMap googleMap;
    private final Context context;
    private final View view;

    private SearchAdapter searchAdapter;
    private BusStopAdapter busAdapter;

    private final ServiceApi serviceProvider;


    private final Map<Integer, Marker> markerMap = new HashMap<>();

    private SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView stopRecyclerView;

    private List<BusStopPositionModel> items = new ArrayList<>();
    ;


    private List<BusStopPositionModel> filteredItems = new ArrayList<>();


    public GoogleMapController(View view, Context context, GoogleMap googleMap) {
        serviceProvider = ServiceProvider.createService(ServiceApi.class);
        this.view = view;
        this.context = context;
        this.googleMap = googleMap;

        initAdapter();
        initBusService();

    }


    public void initAdapter() {
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        stopRecyclerView = view.findViewById(R.id.stopInformationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        stopRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }


    private void fetchBusPositions(int stopCode, int busPrefix) {
        Call<BusPositionResponse> call = serviceProvider.getBusPositions(stopCode);
        call.enqueue(new retrofit2.Callback<BusPositionResponse>() {
            @Override
            public void onResponse(@NonNull Call<BusPositionResponse> call, @NonNull Response<BusPositionResponse> response) {
                if (response.isSuccessful()) {
                    BusPositionResponse busPositionResponse = response.body();
                    if (busPositionResponse != null) {
                        List<BusVehicleModel> busVehicleModels = busPositionResponse.getBusVehicleModels();
                        for (BusVehicleModel busVehicleModel : busVehicleModels) {
                            LatLng latLng = new LatLng(busVehicleModel.getLatitude(), busVehicleModel.getLongitude());
                            int prefix = busVehicleModel.getPrefix();




                            Marker marker = markerMap.get(prefix);
                            if (marker != null) {
                                marker.setPosition(latLng);
                            } else {
                                marker = googleMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(prefix))
                                        .snippet(getAddressFromLocation(busVehicleModel.getLatitude(), busVehicleModel.getLongitude()))
                                        .icon(DrawableUtils.imageToBitmapDescriptor(context, R.drawable.bus_svgrepo_com__1_)));
                                markerMap.put(prefix, marker);
                            }

                            if (prefix == busPrefix) {
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 24));
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BusPositionResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void fetchBusStopPositions() {
        Call<List<BusStopPositionModel>> call = serviceProvider.getStopPositions();

        call.enqueue(new retrofit2.Callback<List<BusStopPositionModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<BusStopPositionModel>> call, @NonNull retrofit2.Response<List<BusStopPositionModel>> response) {
                if (response.isSuccessful()) {
                    List<BusStopPositionModel> busStopPositions = response.body();
                    assert busStopPositions != null;
                    items = new ArrayList<>(busStopPositions);
                    filteredItems = new ArrayList<>(busStopPositions);
                    searchAdapter = new SearchAdapter(filteredItems);
                    recyclerView.setAdapter(searchAdapter);

                    searchAdapter.setOnItemClickListener(item -> {
                        LatLng latLng = new LatLng(item.getLatitude(), item.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 24));
                        searchView.clearFocus();
                        recyclerView.setVisibility(View.GONE);
                    });

                    for (BusStopPositionModel position : busStopPositions) {
                        LatLng latLng = new LatLng(position.getLatitude(), position.getLongitude());


                        googleMap.addMarker(new MarkerOptions().position(latLng).title(position.getName())
                                .snippet(position.getAddress())
                                .icon(DrawableUtils.imageToBitmapDescriptor(context, R.drawable.bus_stop_svgrepo_com)));

                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                Button closeModalButton = view.findViewById(R.id.stopInformation);
                                View stopDetailsContainer = view.findViewById(R.id.stopContainer);

                                if (stopDetailsContainer.getVisibility() == View.GONE) {
                                    DrawableUtils.showLayout(stopDetailsContainer);
                                    closeModalButton.setText(R.string.hide_lines);
                                }

                                String markerTitle = marker.getTitle();
                                items.stream().filter(item -> item.getName().equals(markerTitle)).findFirst().ifPresent((item) -> {
                                    fetchBusData(item.getCode());

                                    stopDetailsContainer.setVisibility(View.VISIBLE);
                                    closeModalButton.setVisibility(View.VISIBLE);

                                    closeModalButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (stopDetailsContainer.getVisibility() == View.VISIBLE) {
                                                DrawableUtils.hideLayout(stopDetailsContainer, closeModalButton);
                                            } else {
                                                DrawableUtils.showLayout(stopDetailsContainer);
                                                closeModalButton.setText(R.string.hide_lines);
                                            }
                                        }
                                    });
                                });

                                return false;
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BusStopPositionModel>> call, @NonNull Throwable t) {
            }
        });
    }


    private void fetchBusData(int stopCode) {
        Call<BusStopResponse> call = serviceProvider.getStopVehicles(stopCode);
        View loader = view.findViewById(R.id.loader);
        View stopList = view.findViewById(R.id.stopInformationList);
        stopList.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        call.enqueue(new retrofit2.Callback<BusStopResponse>() {


            @Override
            public void onResponse(@NonNull Call<BusStopResponse> call, @NonNull Response<BusStopResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    EstimatedBusModel estimatedBus = response.body().getEstimatedBus();
                    if (estimatedBus != null && !estimatedBus.getLines().isEmpty()) {

                        if (busAdapter == null) {
                            busAdapter = new BusStopAdapter(estimatedBus);
                            stopRecyclerView.setAdapter(busAdapter);
                        }

                        busAdapter.setOnItemClickListener((stopCode, busPrefix) -> fetchBusPositions(stopCode, busPrefix));

                        busAdapter.updateList(estimatedBus);
                        View viewById = view.findViewById(R.id.stopInformationList);
                        viewById.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<BusStopResponse> call, @NonNull Throwable throwable) {

            }
        });

    }

    private void initBusService() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredItems.clear();
                if (newText != null && !newText.isEmpty()) {
                    String search = newText.toLowerCase();
                    for (BusStopPositionModel item : items) {
                        if (item.getName().toLowerCase().contains(search)) {
                            filteredItems.add(item);
                        }
                    }
                    recyclerView.setVisibility(View.VISIBLE);

                } else {
                    recyclerView.setVisibility(View.GONE);
                }

                if (filteredItems.isEmpty()) recyclerView.setVisibility(View.GONE);

                searchAdapter.updateList(filteredItems);
                return true;
            }
        });

    }

    private String getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "N/D";
    }

}