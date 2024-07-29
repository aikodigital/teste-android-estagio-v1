//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitInstance {
//    private const val BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1/"
//
//    // Interceptor para adicionar a chave de API
//    private val apiKeyInterceptor = Interceptor { chain ->
//        val original = chain.request()
//        val originalHttpUrl = original.url
//
//        // Adiciona a chave de API como parâmetro da URL
//        val url = originalHttpUrl.newBuilder()
//            .addQueryParameter("api_key", BuildConfig.API_KEY)
//            .build()
//
//        // Request com nova URL
//        val requestBuilder = original.newBuilder().url(url)
//        val request = requestBuilder.build()
//        chain.proceed(request)
//    }
//
//    // Cliente OkHttp com o interceptor
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(apiKeyInterceptor)
//        .build()
//
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val api: OlhoVivoApi by lazy {
//        retrofit.create(OlhoVivoApi::class.java)
//    }
//}