package com.example.aiko_desafio.objetos

// Objeto para receber o JSON das previsões de chegada da classe PrevisãoChegada
data class Chegada(
    var hr : Any? = null,
    var p : parada_previsao
)

data class parada_previsao(
    var cp : Any? = null,
    var np : Any? = null,
    var py : Any? = null,
    var px : Any? = null,
    var l : List<linha_previsao>
)

data class linha_previsao(
    var c : Any? = null,
    var cl : Any? = null,
    var sl : Any? = null,
    var lt0 : Any? = null,
    var lt1 : Any? = null,
    var qv : Any? = null,
    var vs : List<veiculo_previsao>
)

data class veiculo_previsao(
    var p : Any? = null,
    var t : Any? = null,
    var a : Any? = null,
    var ta : Any? = null,
    var py : Any? = null,
    var px : Any? = null
)
