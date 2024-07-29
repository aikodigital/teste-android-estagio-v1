package com.example.projetoestagioaiko.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PontosDeParadaResponse {
    private List<PontoDeParada> pontosDeParada;

    // Getters and Setters

    public List<PontoDeParada> getPontosDeParada() {
        return pontosDeParada;
    }

    public void setPontosDeParada(List<PontoDeParada> pontosDeParada) {
        this.pontosDeParada = pontosDeParada;
    }
}
