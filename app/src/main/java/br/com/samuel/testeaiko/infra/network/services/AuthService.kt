package br.com.samuel.testeaiko.infra.network.services

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    /**
     * Para autenticar-se no serviço de API do Olho Vivo é necessário efetuar uma chamada prévia utilizando o método http POST informando seu token de acesso.
     * Essa chamada irá retornar true quando a autenticação for realizada com sucesso e false em caso de erros.
     */
    @POST("Login/Autenticar")
    suspend fun auth(@Query("token") token: String): Response<String>

}