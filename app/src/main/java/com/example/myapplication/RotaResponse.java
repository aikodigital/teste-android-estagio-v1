package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RotaResponse {
    @SerializedName("rota")
    private List<LatLng> rota;

    public List<LatLng> getRota() {
        return rota;
    }

    public void setRota(List<LatLng> rota) {
        this.rota = rota;
    }
}