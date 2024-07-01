package br.com.aj.message.appaiko.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "corredor_item",primaryKeys = [ "cc"] ,indices = [Index(value = ["cc"], unique = true)])
data class CorredorItem(
  //  @PrimaryKey(autoGenerate = false)
     val cc: Long,
    val nc: String
)