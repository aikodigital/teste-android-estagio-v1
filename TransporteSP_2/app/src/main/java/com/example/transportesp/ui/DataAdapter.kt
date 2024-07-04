package com.example.transportesp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transportesp.R

class DataAdapter(private var dataList: MutableList<String>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataList[position]
    }

    override fun getItemCount() = dataList.size
//    fun setData(newData: List<String>)
    fun setData(newData: List<String>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}
