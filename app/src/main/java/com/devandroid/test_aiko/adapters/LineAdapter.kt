package com.devandroid.test_aiko.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.models.Line

class LineAdapter : RecyclerView.Adapter<LineAdapter.LineViewHolder>() {
    private var lines : List<Line> = listOf()

    fun setLines(lines: List<Line>){
        this.lines = lines
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_line,parent,false)
        return LineViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val line = lines[position]
        holder.textViewLineName.text = line.DenominacaoTPTS
        holder.textViewLineDetails.text = "Código da Linha: ${line.CodigoLinha}, Sentido: ${line.Sentido}"
    }

    class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewLineName: TextView = itemView.findViewById(R.id.textViewLineName)
        val textViewLineDetails: TextView = itemView.findViewById(R.id.textViewLineDetails)
    }
}

