package me.patrick.aikodigital.pontocerto.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class BusLineModel {
    @SerializedName("c")
    private String label;

    @SerializedName("cl")
    private int prefix;


    /**
     * Retorna a direção em que o ônibus está circulando.
     *
     * @return 1 significa de Terminal Principal para Terminal Secundário e 2 de Terminal Secundário para Terminal Principal
     *
     */
    @SerializedName("sl")
    private int direction;

    @SerializedName("lt0")
    private String destinationLabel;

    @SerializedName("lt1")
    private String origenLabel;

    @SerializedName("qv")
    private int amount;

    @SerializedName("vs")
    private List<BusVehicleModel> vehicles;
}
