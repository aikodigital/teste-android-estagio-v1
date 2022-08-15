package br.com.aj.message.appaiko.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "parada_item")
data class Parada(
    @PrimaryKey(autoGenerate = true)
    var id:Long?,
    val cp: Int,
    @Ignore val l: MutableList<L>?,
    val ed: String?,
    val np: String,
    val px: Double,
    val py: Double
){
    constructor(
                id:Long?,
                 cp: Int,
                 ed: String?,
                 np: String,
                 px: Double,
                 py: Double):this(id, cp, null, ed, np, px, py)
}