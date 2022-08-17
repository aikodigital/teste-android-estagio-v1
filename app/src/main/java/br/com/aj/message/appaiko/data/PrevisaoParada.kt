package br.com.aj.message.appaiko.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "previsao_parada")
data class PrevisaoParada(
    @PrimaryKey(autoGenerate = true)
    var id:Long?,
    val hr: String,
   @Ignore val p: Parada?
){
    constructor( id:Long?,
                 hr: String,
    ):this(id,hr,null)
}

