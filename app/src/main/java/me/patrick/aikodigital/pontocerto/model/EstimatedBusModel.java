package me.patrick.aikodigital.pontocerto.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class EstimatedBusModel {

    @SerializedName("cp")
    private long code;

    @SerializedName("np")
    private String name;

    @SerializedName("py")
    private double latitude;

    @SerializedName("px")
    private double longitude;

    @SerializedName("l")
    private List<BusLineModel> lines;

}