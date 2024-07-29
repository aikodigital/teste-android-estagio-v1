package com.example.bustrackingsp.api.entities;

public class Empresa {
    private String hora;
    private EmpresaPorArea empresaPorArea;

    public Empresa(String hora, EmpresaPorArea empresaPorArea) {
        this.hora = hora;
        this.empresaPorArea = empresaPorArea;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public EmpresaPorArea getEmpresaPorArea() {
        return empresaPorArea;
    }

    public void setEmpresaPorArea(EmpresaPorArea empresaPorArea) {
        this.empresaPorArea = empresaPorArea;
    }
}
