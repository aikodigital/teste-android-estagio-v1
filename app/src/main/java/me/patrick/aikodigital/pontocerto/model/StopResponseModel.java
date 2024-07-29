package me.patrick.aikodigital.pontocerto.model;

import java.util.List;

import lombok.Data;

@Data
public class StopResponseModel {
    List<BusStopPositionModel> positions;
}
