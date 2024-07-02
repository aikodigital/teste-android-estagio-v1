package com.example.desafio_aiko_2024.model;

public class BusModel {
    private String busLine;
    private boolean isCircular;
    private int direction;
    private String mainTerminal;
    private String secondaryTerminal;

    public BusModel(String busLine, boolean isCircular, int direction, String mainTerminal, String secondaryTerminal) {
        this.busLine = busLine;
        this.isCircular = isCircular;
        this.direction = direction;
        this.mainTerminal = mainTerminal;
        this.secondaryTerminal = secondaryTerminal;
    }

    public String getBusLine() {
        return busLine;
    }

    public boolean isCircular() {
        return isCircular;
    }

    public int getDirection() {
        return direction;
    }

    public String getMainTerminal() {
        return mainTerminal;
    }

    public String getSecondaryTerminal() {
        return secondaryTerminal;
    }
}
