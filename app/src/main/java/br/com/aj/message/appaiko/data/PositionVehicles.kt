package br.com.aj.message.appaiko.data

import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "position_vehicles")
data class PositionVehicles(
    @PrimaryKey(autoGenerate = true)
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
    foreignKeys = [
        ForeignKey(
            entity = PositionVehicles::class,
            parentColumns = ["id"],
            childColumns = ["fid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            false
        )
    ]
)
data class L(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    val c: String,
    val cl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val sl: Int,
    @Ignore var vs: MutableList<V>?,
    val fid: Long?
) {
    constructor(
        id: Long?,
        c: String,
        cl: Int,
        lt0: String,
        lt1: String,
        qv: Int,
        sl: Int,
        fid: Long?
    ) : this(
        id,
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
    foreignKeys = [
        ForeignKey(
            entity = L::class,
            parentColumns = ["id"],
            childColumns = ["fid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            false
        )
    ]
)
data class V(
    @PrimaryKey(
        autoGenerate = true,
    )
    var id: Long?,
    val a: Boolean,
    val t: String?,
    val p: Int,
    val px: Double,
    val py: Double,
    val ta: String,
    val fid: Long?
)


class ConvertersPositionVehicles {

    @TypeConverter
    fun listToJson(value: List<L>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<L>::class.java).toList()
}
