package com.example.bustrackingsp.api.entities;

import java.util.List;

public class PrevisaoParada {
    private int cp;
    private String np;
    private double py;
    private double px;
    private List<PrevisaoLinhas> l;

    public PrevisaoParada(int cp, String np, double py, double px, List<PrevisaoLinhas> l) {
        this.cp = cp;
        this.np = np;
        this.py = py;
        this.px = px;
        this.l = l;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getNp() {
        return np;
    }

    public void setNp(String np) {
        this.np = np;
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

    public List<PrevisaoLinhas> getL() {
        return l;
    }

    public void setL(List<PrevisaoLinhas> l) {
        this.l = l;
    }
}
