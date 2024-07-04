package com.example.desafio_aiko_2024.model;

import com.google.gson.annotations.SerializedName;

public class BusLineModel {
    @SerializedName("cl")
    private int id;
    @SerializedName("lc")
    private boolean circular;
    @SerializedName("lt")
    private String lineNumber;
    @SerializedName("tl")
    private int type;
    @SerializedName("tp")
    private String mainTerminal;
    @SerializedName("ts")
    private String secondaryTerminal;

    public BusLineModel(int id, boolean circular, String lineNumber, int type, String mainTerminal, String secondaryTerminal) {
        this.id = id;
        this.circular = circular;
        this.lineNumber = lineNumber;
        this.type = type;
        this.mainTerminal = mainTerminal;
        this.secondaryTerminal = secondaryTerminal;
    }

    public int getId() {
        return id;
    }

    public boolean isCircular() {
        return circular;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public int getType() {
        return type;
    }

    public String getMainTerminal() {
        return mainTerminal;
    }

    public String getSecondaryTerminal() {
        return secondaryTerminal;
    }
}
