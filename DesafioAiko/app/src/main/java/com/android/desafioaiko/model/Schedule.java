package com.android.desafioaiko.model;

import java.util.List;

public class Schedule {

    private String c; // Letreiro completo
    private int cl; // Codigo da linha
    private int sl; // Sentido de operação
    private String lt0; //Letreiro de destino
    private String lt1; //Letreiro de origem
    private int qv; //quantidade de veiculos
    private List<BusScheduleModel> vs; // Lista de horarios dos ônibus

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public int getCl() {
        return cl;
    }

    public void setCl(int cl) {
        this.cl = cl;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getLt0() {
        return lt0;
    }

    public void setLt0(String lt0) {
        this.lt0 = lt0;
    }

    public String getLt1() {
        return lt1;
    }

    public void setLt1(String lt1) {
        this.lt1 = lt1;
    }

    public int getQv() {
        return qv;
    }

    public void setQv(int qv) {
        this.qv = qv;
    }

    public List<BusScheduleModel> getVs() {
        return vs;
    }

    public void setVs(List<BusScheduleModel> vs) {
        this.vs = vs;
    }
}
