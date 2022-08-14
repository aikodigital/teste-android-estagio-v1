package br.com.daniel.aikoandroidestagio.model

import android.util.Log
import java.io.Serializable

data class LocalizacaoVeiculos(
    val hr: String,
    val l: List<L>
) : Serializable {
    var total = 0

    fun isEmpty(): Boolean {
        return l.isEmpty()
    }

    fun carregarDados() {
        total = 0
        var indexTotal = 0

        //Linha por linha
        l.forEachIndexed { indexL, linha ->
            total += linha.vs.size

            //Veiculo por veiculo
            linha.vs.forEachIndexed { index, veiculo ->
                if (linha.sl == 1) {
                    veiculo.destino = linha.lt0
                    veiculo.origem = linha.lt1
                } else {
                    veiculo.destino = linha.lt1
                    veiculo.origem = linha.lt0
                }
                if (indexL == 0) {
                    //Primeira linha
                    veiculo.position = index
                    indexTotal = index
                } else {
                    //outras linhas
                    indexTotal += 1
                    veiculo.position = indexTotal
                }
            }
        }
    }
}