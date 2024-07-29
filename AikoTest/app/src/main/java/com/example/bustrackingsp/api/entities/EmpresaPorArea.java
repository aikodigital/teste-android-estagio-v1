package com.example.bustrackingsp.api.entities;

public class EmpresaPorArea {
    private int idArea;
    private RelacaoEmpresa relacaoEmpresa;

    public EmpresaPorArea(int idArea, RelacaoEmpresa relacaoEmpresa) {
        this.idArea = idArea;
        this.relacaoEmpresa = relacaoEmpresa;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public RelacaoEmpresa getRelacaoEmpresa() {
        return relacaoEmpresa;
    }

    public void setRelacaoEmpresa(RelacaoEmpresa relacaoEmpresa) {
        this.relacaoEmpresa = relacaoEmpresa;
    }
}
