package com.conti.onibusspemtemporeal.data.retrofit

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*

/** Classe para Gerenciar os cookies com a implementação da interface CookieJar
 *  ela permite o armazenamentos dos cookies, o que é de grande importancia para consumir a api Olho vivo,
 *  pois preciso salvar o cookie [apiCredentials] disponibilizado caso o request de Post do Login retorne True
 *  após o login é possivel persistir o cookie na instancia do retrofit e utiliza-lo nos outros request */
class ManagerCookiesWithCookieJar : CookieJar {

    //MutableList de [Cookie] para armazenar os cookies
    private var cookiesList: MutableList<Cookie> = mutableListOf()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        //Adicionar todos os cookies da request em [cookieList]
        this.cookiesList.addAll(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (cookiesList.isNotEmpty()) {
            //retorna os cookies que foram salvo na ultima request
            return cookiesList
        }
        //retornar uma lista vázia caso não tenha sido salvo nenhum cookie
        return Collections.emptyList();
    }

}