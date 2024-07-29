package com.example.bustrackingsp.api.entities;

public class Linha {
    private int cl;
    private boolean lc;
    private String lt;
    private int tl;
    private int sl;
    private String tp;
    private String ts;

    public Linha(int cl, boolean lc, String lt, int tl, int sl, String tp, String ts) {
        this.cl = cl;
        this.lc = lc;
        this.lt = lt;
        this.tl = tl;
        this.sl = sl;
        this.tp = tp;
        this.ts = ts;
    }

    public int getCl() {
        return cl;
    }

    public void setCl(int cl) {
        this.cl = cl;
    }

    public boolean isLc() {
        return lc;
    }

    public void setLc(boolean lc) {
        this.lc = lc;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}

