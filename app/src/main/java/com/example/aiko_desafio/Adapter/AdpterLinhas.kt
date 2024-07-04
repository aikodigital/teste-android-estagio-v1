package com.example.aiko_desafio.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aiko_desafio.databinding.LinhaItemBinding
import com.example.aiko_desafio.objetos.Linhas

// Classe para imprimir as informações na tela.
class AdpterLinhas(private val context : Context, private val listaLinha : List<Linhas>) :
    RecyclerView.Adapter<AdpterLinhas.LinhaViewHolder>() {

        // Função que cria os itens.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaViewHolder {
        val itemLinha = LinhaItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return LinhaViewHolder(itemLinha)

    }

    // Função que exibe os itens na tela.
    override fun onBindViewHolder(holder: LinhaViewHolder, position: Int) {
        var linha = "Código identificador: "+ listaLinha[position].cl.toString()
        holder.txtLinhas.text = linha
        var circular = "Circular: "+ formatar(listaLinha[position].lc.toString())
        holder.txtCircular.text = circular
        var sentido = "Sentido da linha: "+ formatar(listaLinha[position].sl.toString())
        holder.txtSentido.text = sentido
        var letreironum = "Letreiro: "+listaLinha[position].lt+"-"+listaLinha[position].tl.toString()
        holder.txtLetreiroNum.text = letreironum
        var letreirotp = "Letreiro sentido Terminal Principal  para Terminal Secundário: "+listaLinha[position].tp
        holder.txtLetreiroDescTP.text = letreirotp
        var letreirots = "Letreiro sentido Terminal Secundário para Terminal Principal: "+listaLinha[position].ts
        holder.txtLetreiroDescTS.text = letreirots
    }

    // Função que retorna o tamanho da lista.
    override fun getItemCount() = listaLinha.size

    // Função que retorna os valores a serem impressos
    inner class LinhaViewHolder(b: LinhaItemBinding): RecyclerView.ViewHolder(b.root) {
        val txtLinhas = b.txtLinhas;
        val txtCircular = b.txtCircular;
        val txtSentido = b.txtSentido
        val txtLetreiroNum = b.txtLetreiroNum
        val txtLetreiroDescTP = b.txtLetreiroDescTP
        val txtLetreiroDescTS = b.txtLetreiroDescTS

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
        // Retorna o texto.
        return texto
    }
}