package com.example.bustrackingsp.api.entities;

public class RelacaoVeiculo {
    private int p;
    private boolean a;
    private String ta;
    private double py;
    private double px;

    public RelacaoVeiculo(int p, boolean a, String ta, double py, double px) {
        this.p = p;
        this.a = a;
        this.ta = ta;
        this.py = py;
        this.px = px;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
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