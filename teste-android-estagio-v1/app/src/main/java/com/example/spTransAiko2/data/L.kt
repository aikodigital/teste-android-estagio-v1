package com.example.spTransAiko2.data

import com.google.gson.annotations.SerializedName

data class L (

    @SerializedName("c"   ) var c   : String?       = null,
    @SerializedName("cl"  ) var cl  : Int?          = null,
    @SerializedName("sl"  ) var sl  : Int?          = null,
    @SerializedName("lt0" ) var lt0 : String?       = null,
    @SerializedName("lt1" ) var lt1 : String?       = null,
    @SerializedName("qv"  ) var qv  : Int?          = null,
    @SerializedName("vs"  ) var vs  : ArrayList<Vs> = arrayListOf()

)