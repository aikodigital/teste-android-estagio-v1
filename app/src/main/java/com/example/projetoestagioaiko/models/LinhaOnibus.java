package com.example.projetoestagioaiko.models;

import com.google.gson.annotations.SerializedName;


public class LinhaOnibus {

    private String nome;
    private String codigo;
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
