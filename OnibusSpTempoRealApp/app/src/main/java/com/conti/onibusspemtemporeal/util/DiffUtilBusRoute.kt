package com.conti.onibusspemtemporeal.util

import androidx.recyclerview.widget.DiffUtil
import com.conti.onibusspemtemporeal.data.models.BusRoute

class DiffUtilBusRoute : DiffUtil.ItemCallback<BusRoute>() {

    override fun areItemsTheSame(oldItem: BusRoute, newItem: BusRoute): Boolean {
        return oldItem.lineWay == newItem.lineWay && oldItem.lineCod == newItem.lineCod
    }

    override fun areContentsTheSame(oldItem: BusRoute, newItem: BusRoute): Boolean {
        return newItem == oldItem
    }
}
