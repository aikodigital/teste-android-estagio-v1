package com.example.aiko_desafio.objetos

// Objeto para receber o JSON das linhas da classe Tela_Linhas
data class Linhas(
    var cl : Int = 0,
    var lc: Boolean = false,
    var lt : String = "",
    var sl : Int = 0,
    var tl : Int = 0,
    var tp : String =  "",
    var ts : String =  "",
)