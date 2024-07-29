package com.example.bustrackingsp.api.entities;

public class Parada {
    private int cp;
    private String np;
    private String ed;
    private double py;
    private double px;

    public Parada(int cp, String np, String ed, double py, double px) {
        this.cp = cp;
        this.np = np;
        this.ed = ed;
        this.py = py;
        this.px = px;
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
