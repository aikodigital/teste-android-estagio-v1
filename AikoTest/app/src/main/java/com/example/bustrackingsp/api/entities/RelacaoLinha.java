package com.example.bustrackingsp.api.entities;

import java.util.List;

public class RelacaoLinha {
    private String c;
    private int cl;
    private int sl;
    private String lt0;
    private String lt1;
    private int qv;
    private List<RelacaoVeiculo> vs;

    public RelacaoLinha(String c, int cl, int sl, String lt0, String lt1, int qv, List<RelacaoVeiculo> vs) {
        this.c = c;
        this.cl = cl;
        this.sl = sl;
        this.lt0 = lt0;
        this.lt1 = lt1;
        this.qv = qv;
        this.vs = vs;
    }

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

    public List<RelacaoVeiculo> getVs() {
        return vs;
    }

    public void setVs(List<RelacaoVeiculo> vs) {
        this.vs = vs;
    }
}