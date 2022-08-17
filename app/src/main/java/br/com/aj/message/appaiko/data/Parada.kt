package br.com.aj.message.appaiko.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "parada_item" , primaryKeys = [ "cp"] , indices = [Index(value = ["cp"], unique = true)])
data class Parada(
   // @PrimaryKey(autoGenerate = false)
    val cp: Long,
    @Ignore val l: MutableList<L>?,
    val ed: String?,
    val np: String,
    val px: Double,
    val py: Double
){
    constructor(

                 cp: Long,
                 ed: String?,
                 np: String,
                 px: Double,
                 py: Double):this( cp, null, ed, np, px, py)
}