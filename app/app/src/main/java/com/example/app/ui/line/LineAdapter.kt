package com.example.app.ui.line

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.LineItemBinding
import com.example.app.domain.model.Line

class LineAdapter(
    private val lines: List<Line>
) : RecyclerView.Adapter<LineAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = lines.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val line = lines[position]

        holder.binding.apply {
            tvLineCode.text = line.lineCode.toString()
            tvFrom.text = line.lineOrigin
            tvTo.text = line.lineDestination
        }
    }

    inner class MyViewHolder(val binding: LineItemBinding) : RecyclerView.ViewHolder(binding.root)
}