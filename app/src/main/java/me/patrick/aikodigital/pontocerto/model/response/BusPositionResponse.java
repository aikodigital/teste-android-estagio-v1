package me.patrick.aikodigital.pontocerto.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import me.patrick.aikodigital.pontocerto.model.BusVehicleModel;

@Data
public class BusPositionResponse {
    @SerializedName("hr")
    private String hour;

    @SerializedName("vs")
    private List<BusVehicleModel> busVehicleModels;

}
