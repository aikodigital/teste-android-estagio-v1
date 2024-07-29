package com.example.projetoestagioaiko.models;

import com.google.gson.annotations.SerializedName;

public class PrevisaoChegada {
    private String previsao;
    private String veiculo;


    public String getPrevisao() {
        return previsao;
    }

    public void setPrevisao(String previsao) {
        this.previsao = previsao;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }
}

