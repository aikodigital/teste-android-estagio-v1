package com.android.desafioaiko.model;

public class BusScheduleModel {
    private String p; //Prefixo do veiculo
    private String t; // Horario de chegada do veiculo
    private boolean a; // Acessivel a pessoas com deficiencia
    private String ta; // Horario de captura
    private double py; // Latitude do veiculo
    private double px; // Longitude do veiculo

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public double getPy() {
        return py;
    }

    public void setPy(double py) {
        this.py = py;
    }

    public double getPx() {
        return px;
    }

    public void setPx(double px) {
        this.px = px;
    }
}
