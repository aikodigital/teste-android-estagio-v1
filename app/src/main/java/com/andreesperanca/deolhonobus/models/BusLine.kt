package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoritesBusLine")
@Parcelize
data class BusLine(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerializedName("cl")
    val idCode: Int,
    @SerializedName("lc")
    val CircularRoute: Boolean,
    @SerializedName("lt")
    val firstLabel : String,
    @SerializedName("tl")
    val secondLabel: String,
    @SerializedName("sl")
    val direction: Int,
    @SerializedName("tp")
    val mainTerminal: String,
    @SerializedName("ts")
    val secondaryTerminal: String
) : Parcelable
