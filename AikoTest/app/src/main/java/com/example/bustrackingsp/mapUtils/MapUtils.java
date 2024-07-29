package com.example.bustrackingsp.mapUtils;

import com.example.bustrackingsp.api.entities.Linha;
import com.example.bustrackingsp.api.entities.Parada;
import com.example.bustrackingsp.api.entities.Posicao;
import com.example.bustrackingsp.api.entities.Previsao;
import com.example.bustrackingsp.api.entities.PrevisaoLinhas;
import com.example.bustrackingsp.api.entities.PrevisaoVeiculos;
import com.example.bustrackingsp.api.entities.RelacaoLinha;
import com.example.bustrackingsp.api.entities.RelacaoVeiculo;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MapUtils {
    private static LatLng startingPosition = new LatLng(-23.5505, -46.6333);

    // 1 = Posicao
    private static int lastMapMode = 0;
    private static int lastTextMode = 0;

    private static List<Parada> paradas = new ArrayList<>();

    private static Posicao posicao;
    private static List<LatLng> vehiclesPosMarkers = new ArrayList<>();

    private static List<Linha> linhas = new ArrayList<>();

    private static Previsao previsao;

    public static Previsao getPrevisao() {
        return previsao;
    }

    public static void setPrevisao(Previsao previsao) {
        MapUtils.previsao = previsao;
    }

    public static String getPrevisaoText(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("Hora atual: " + previsao.getHr() + "\n")
                .append("Parada: " + previsao.getP().getNp() + "(Cod: " + previsao.getP().getCp() + ")\n\n");

        for(PrevisaoLinhas l : previsao.getP().getL()){
            finalString.append("Onibus:\nLetreiro: " + l.getC() + " Linha: " + l.getCl()+ "\n")
                    .append(l.getLt0() + " -- " + l.getLt1() + "\nPrevisoes:\n");
            for(PrevisaoVeiculos pv : l.getVs()){
                finalString.append("Veiculo: " + pv.getP() + " -- Hora: " + pv.getT() + "\n");
            }
            finalString.append("\n");
        }

        return finalString.toString();
    }



    public static String getLinhasFormatado(){
        StringBuilder finalString = new StringBuilder();
        for(Linha linha : linhas){
            finalString.append("identificador da linha: ").append(linha.getCl()).append("\n")
                    .append("modo circular: ").append(linha.isLc()).append("\n")
                    .append("letreiro: ").append(linha.getLt()).append("-").append(linha.getTl()).append("\n")
                    .append("modo: ").append(linha.getSl()).append("\n")
                    .append("Terminal Principal: ").append(linha.getTp()).append("\n")
                    .append("Terminal Secundario: ").append(linha.getTs()).append("\n\n");
        }
        return finalString.toString();
    }

    public static List<Linha> getLinhas() {
        return linhas;
    }

    public static void setLinhas(List<Linha> linhas) {
        MapUtils.linhas = linhas;
    }

    public static List<Parada> getParadas() {
        return paradas;
    }

    public static void setParadas(List<Parada> paradas) {
        MapUtils.paradas = paradas;
    }

    public static void setVehiclesPosMarkers(){
        List<LatLng> newMarkers = new ArrayList<>();
        for(RelacaoLinha relacaoLinha : posicao.getL()){
            for(RelacaoVeiculo relacaoVeiculo : relacaoLinha.getVs()){
                newMarkers.add(new LatLng(relacaoVeiculo.getPy(), relacaoVeiculo.getPx()));
            }
        }
        vehiclesPosMarkers = newMarkers;
    }

    public static List<LatLng> getVehiclesPosMarkers() {
        return vehiclesPosMarkers;
    }

    public static int getLastMapMode() {
        return lastMapMode;
    }

    public static void setLastMapMode(int lastMode) {
        MapUtils.lastMapMode = lastMode;
    }

    public static Posicao getPosicao() {
        return posicao;
    }

    public static void setPosicao(Posicao posicao) {
        MapUtils.posicao = posicao;
    }

    public static int getLastTextMode() {
        return lastTextMode;
    }

    public static void setLastTextMode(int lastTextMode) {
        MapUtils.lastTextMode = lastTextMode;
    }
}
