package br.com.aj.message.appaiko.data

import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "position_vehicles")
data class PositionVehicles(
    @PrimaryKey(autoGenerate = false)
    var id: Long?,
    val hr: String,
    @Ignore var l: MutableList<L>?
) {
    constructor(
        id: Long?,
        hr: String,
    )
            : this(id, hr, null)

}

@Entity(
    tableName = "position_vehicles_l",
    indices = [Index(value = ["cl"], unique = true)],
    primaryKeys = [ "cl"],
         /*   foreignKeys = [
        ForeignKey(
            entity = PositionVehicles::class,
            parentColumns = ["id"],
            childColumns = ["fid"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION,
            false
        )
    ] */
)
data class L(

    val c: String,
  //  @PrimaryKey(autoGenerate = false)
 val cl: Long,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val sl: Int,
    @Ignore var vs: MutableList<V>?,
    var fid: Long?
) {
    constructor(
        c: String,
        cl: Long,
        lt0: String,
        lt1: String,
        qv: Int,
        sl: Int,
        fid: Long?
    ) : this(
        c,
        cl,
        lt0,
        lt1,
        qv,
        sl,
        null,
        fid
    )
}


@Entity(
    tableName = "position_vehicles_v",
    indices = [Index(value = ["p"], unique = true)],
    primaryKeys = [ "p"]
)
data class V(


    val a: Boolean,
    val t: String?,
   // @PrimaryKey(autoGenerate = false)
     val p: Long,
    val px: Double,
    val py: Double,
    val ta: String,
    var fid: Long?
)


class ConvertersPositionVehicles {

    @TypeConverter
    fun listToJson(value: List<L>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<L>::class.java).toList()
}
