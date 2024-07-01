package com.conti.onibusspemtemporeal.data.retrofit.interfaces


interface OlhoVivoApiAuthenticateInterface {

    suspend fun postAuthenticate(): Boolean
}