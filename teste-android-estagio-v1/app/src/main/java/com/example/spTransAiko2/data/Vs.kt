package com.example.spTransAiko2.data

import com.google.gson.annotations.SerializedName

data class Vs (

    @SerializedName("p"  ) var p  : Int?     = null,
    @SerializedName("a"  ) var a  : Boolean? = null,
    @SerializedName("ta" ) var ta : String?  = null,
    @SerializedName("py" ) var py : Double?  = null,
    @SerializedName("px" ) var px : Double?  = null

)