package com.example.spTransAiko2.data

import com.example.spTransAiko2.data.L
import com.google.gson.annotations.SerializedName

data class Posicao (

    @SerializedName("hr" ) var hr : String?      = null,
    @SerializedName("l"  ) var l  : ArrayList<L> = arrayListOf()

)
