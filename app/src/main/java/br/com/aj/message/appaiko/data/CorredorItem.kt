package br.com.aj.message.appaiko.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "corredor_item")
data class CorredorItem(
    @PrimaryKey(autoGenerate = true)
    var id:Long?,
    val cc: Int,
    val nc: String
)