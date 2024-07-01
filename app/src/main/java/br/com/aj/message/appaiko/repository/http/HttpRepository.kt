package br.com.aj.message.appaiko.repository.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HttpRepository(private  val api:Routes) {
    suspend fun getAllPositionVehicles() = withContext(Dispatchers.IO){ api.all() }
    suspend fun getAuth() = withContext(Dispatchers.IO){   api.autenticar(ApiRoutes.KEY)}
   suspend fun corredor() = withContext(Dispatchers.IO){  api.corrredor()}
   suspend fun buscarParadasPorCorredor(id:String) = withContext(Dispatchers.IO){  api.buscarParadasPorCorredor(id)}
    suspend fun previsionBusParada(id:String) = withContext(Dispatchers.IO){  api.previsionBus(id)}

}

fun <T> Call<T>.then(sucess:(T)->Unit,error:(String)->Unit) {

    enqueue(object : Callback<T>{
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful){
                response.body()?.let {  sucess.invoke(it)  }
            }
        }
        override fun onFailure(call: Call<T>, t: Throwable) {
           t.cause?.message?.let {   error.invoke(it) }
        }
    })

}






