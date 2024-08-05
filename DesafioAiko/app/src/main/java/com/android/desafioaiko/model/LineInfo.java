package com.android.desafioaiko.model;

public class LineInfo {

    private int cl; //Codigo da linha
    private boolean lc; //Opera no modo circular
    private String lt; //Letreiro da linha
    private int sl; // Sentido de atendimento
    private String tp; // Letreiro de ida
    private String ts; // Letreiro de volta

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

    @Override
    public String toString() {
        return "LineInfo{" +
                "cl=" + cl +
                ", lc=" + lc +
                ", lt='" + lt + '\'' +
                ", sl=" + sl +
                ", tp='" + tp + '\'' +
                ", ts='" + ts + '\'' +
                '}';
    }
}
