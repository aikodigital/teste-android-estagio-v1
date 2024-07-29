package me.patrick.aikodigital.pontocerto.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BusVehicleModel {
    @SerializedName("p")
    private int prefix;

    @SerializedName("t")
    private String estimatedTime;

    @SerializedName("a")
    private boolean accessible;

    @SerializedName("ta")
    private String captureTime;

    @SerializedName("py")
    private double latitude;

    @SerializedName("px")
    private double longitude;
}
