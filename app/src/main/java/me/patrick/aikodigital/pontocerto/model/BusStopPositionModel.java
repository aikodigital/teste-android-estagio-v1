package me.patrick.aikodigital.pontocerto.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BusStopPositionModel {

    @SerializedName("cp")
    private int code;

    @SerializedName("np")
    private String name;

    @SerializedName("ed")
    private String address;

    @SerializedName("py")
    private double latitude;

    @SerializedName("px")
    private double longitude;

    @NonNull
    @Override
    public String toString(){
        return this.name;
    }
}
