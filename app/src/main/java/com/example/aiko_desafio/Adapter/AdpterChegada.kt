package com.example.aiko_desafio.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aiko_desafio.databinding.ParadaItemBinding
import com.example.aiko_desafio.objetos.Chegada

// Classe para imprimir as informações na tela.
class AdpterChegada(private val context : Context, private val listaChegada : List<Chegada>) :
    RecyclerView.Adapter<AdpterChegada.ChegadaViewHolder>() {

        // Função que cria os itens.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChegadaViewHolder {
        val itemChegada = ParadaItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ChegadaViewHolder(itemChegada)
    }

    // Função que exibe os itens na tela.
    override fun onBindViewHolder(holder: ChegadaViewHolder, position: Int) {
        val chegada = listaChegada[position]
        val parada = chegada.p
        val linha = parada.l[0]
        val veiculo = linha.vs[0]

        val prefixoVeiculo = "Prefixo do veiculo: ${veiculo.p}"
        holder.txtPrefixo.text = prefixoVeiculo

        val horarioChegada = "Horario previsto para a chegada: ${veiculo.t}"
        holder.txtPrevisao.text = horarioChegada

        val ac = "Acessível para pessoas com deficiência: ${formatar(veiculo.a.toString())}"
        holder.txtAc.text = ac
    }


    // Função que retorna o tamanho da lista.
    override fun getItemCount() = listaChegada.size

    // Função que retorna os valores a serem impressos
    inner class ChegadaViewHolder(b: ParadaItemBinding): RecyclerView.ViewHolder(b.root) {
        val txtPrefixo = b.txtPrefixo
        val txtPrevisao= b.txtHorarioprevisto
        val txtAc = b.txtAcessibilidade
    }

    // Função que eu criei para formatar valores como "true" para "sim" ou 2 para "TS para TP"
    fun formatar(escolha : String): String {
        val texto = when(escolha){
            true.toString()->"Sim"
            false.toString()->"Não"
            1.toString()->"TP para TS"
            2.toString()->"TS para TP"
            else->escolha
        }
        return texto
    }
}