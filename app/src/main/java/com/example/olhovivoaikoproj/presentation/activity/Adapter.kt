package com.example.olhovivoaikoproj.presentation.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.olhovivoaikoproj.R
import com.example.olhovivoaikoproj.data.response.Linha

class Adapter :
    RecyclerView.Adapter<Adapter.MyViewlHolder>()  {

    private val linhas: ArrayList<Linha> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewlHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return MyViewlHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewlHolder, position: Int) {
        val prefixo = linhas[position].lt
        val codigoLinha = linhas[position].cl
        val terminalPrincipal = linhas[position].tp
        val terminalSecundario = linhas[position].ts

        holder.p_prefixo.text = "Prefixo: $prefixo"
        holder.cl_codigoLinha.text = "Codigo linha: $codigoLinha"
        holder.tp_TerminalPrincipal.text = "Terminal principal: $terminalPrincipal"
        holder.ts_TerminalSecundario.text = "Terminal secundário: $terminalSecundario"
    }

    override fun getItemCount() = linhas.size

    fun setListLinhas(list: List<Linha>){
        linhas.clear()
        linhas.addAll(list)
        notifyDataSetChanged()

    }

    class MyViewlHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val p_prefixo: TextView = itemView.findViewById(R.id.p_prefixo)
        val cl_codigoLinha: TextView = itemView.findViewById(R.id.cl_codigoLinha)
        val tp_TerminalPrincipal: TextView = itemView.findViewById(R.id.tp_TerminalPrincipal)
        val ts_TerminalSecundario: TextView = itemView.findViewById(R.id.ts_TerminalSecundario)

    }

}