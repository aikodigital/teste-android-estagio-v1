package com.example.transportesp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportesp.data.Line
import com.example.transportesp.R

class LinesAdapter(private val lines: List<Line>) :
    RecyclerView.Adapter<LinesAdapter.LinesViewHolder>() {

    class LinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lineName: TextView = itemView.findViewById(R.id.lineName)
        val lineDetails: TextView = itemView.findViewById(R.id.lineDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_line, parent, false)
        return LinesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinesViewHolder, position: Int) {
        val line = lines[position]
        holder.lineName.text = "Code: ${line.cl}"
        holder.lineDetails.text = "${line.tp} - ${line.ts}"
    }

    override fun getItemCount(): Int = lines.size
}
