package com.example.bustrackingsp.api.entities;

import java.util.List;

public class Posicao {
    private String hr;
    private List<RelacaoLinha> l;

    public Posicao(String hr, List<RelacaoLinha> l) {
        this.hr = hr;
        this.l = l;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public List<RelacaoLinha> getL() {
        return l;
    }

    public void setL(List<RelacaoLinha> l) {
        this.l = l;
    }
}

