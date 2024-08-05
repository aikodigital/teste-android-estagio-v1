package com.android.desafioaiko.model;

public class BusStop {
    private String cp; //Codigo da parada
    private String np; //Nome da parada
    private String ed; //Endereço da parada
    private double py; // Latitude da parada
    private double px; // Longitude da parada

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getNp() {
        return np;
    }

    public void setNp(String np) {
        this.np = np;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
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
