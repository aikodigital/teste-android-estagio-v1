package com.example.bustrackingsp.api.entities;

public class RelacaoEmpresa {
    private int idArea;
    private int idEmpresa;
    private String nomeEmpresa;

    public RelacaoEmpresa(int idArea, int idEmpresa, String nomeEmpresa) {
        this.idArea = idArea;
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
}
