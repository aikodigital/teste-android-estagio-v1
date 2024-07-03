package com.example.app.ui.line

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.LineItemBinding
import com.example.app.domain.model.Line

class LineAdapter(
    private val lines: List<Line>
) : RecyclerView.Adapter<LineAdapter.MyViewHolder>() {
    private var filteredLines: List<Line> = lines

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = filteredLines.size

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredLines = if (query.isEmpty()) {
            lines
        } else {
            lines.filter {
                it.lineCode.toString().contains(query, ignoreCase = true) ||
                it.lineDestination.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val line = filteredLines[position]

        holder.binding.apply {
            tvLineCode.text = line.lineCode.toString()
            tvOrigin.text = line.lineOrigin
            tvDestination.text = line.lineDestination
        }
    }

    inner class MyViewHolder(val binding: LineItemBinding) : RecyclerView.ViewHolder(binding.root)
}