package me.patrick.aikodigital.pontocerto.model.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import me.patrick.aikodigital.pontocerto.model.EstimatedBusModel;

@Data
public class BusStopResponse {
    @SerializedName("hr")
    private String hours;
    @SerializedName("p")
    private EstimatedBusModel estimatedBus;
}
