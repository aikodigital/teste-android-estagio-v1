package com.example.bustrackingsp.api.entities;

public class Previsao {
    private String hr;
    private PrevisaoParada p;

    public Previsao(String hr, PrevisaoParada p) {
        this.hr = hr;
        this.p = p;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public PrevisaoParada getP() {
        return p;
    }

    public void setP(PrevisaoParada p) {
        this.p = p;
    }
}
