package br.com.alanecher.desafioandroid.api

import android.util.Log
import br.com.alanecher.desafioandroid.BuildConfig
import br.com.alanecher.desafioandroid.domain.CharacterDataWrapper
import br.com.alanecher.desafioandroid.domain.ComicDataWrapper
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest

interface MarvelAPI {

    @GET("characters")
    fun listaPersonagens(): Call<CharacterDataWrapper>

    @GET("characters/{characterId}/comics")
    fun listaHQPorPersonagem(@Path("characterId") characterId:String, @Query("offset") offset:Int, @Query("limit") limit:Int): Call<ComicDataWrapper>

    companion object {
        private const val URL = "https://gateway.marvel.com/v1/public/"
        fun criaAPI(): MarvelAPI = criaAPI(HttpUrl.parse(URL)!!)
        fun geraHash(ts:Long):String {
            val hasStr="$ts${BuildConfig.privateKey+BuildConfig.apikey}"
            val m= MessageDigest.getInstance("MD5")
            return BigInteger(1, m.digest(hasStr.toByteArray())).toString(16)
        }
        fun criaAPI(httpUrl: HttpUrl): MarvelAPI {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            val headerInterceptor = Interceptor {
                val original = it.request()
                val originalHttpUrl = original.url()
                val ts=System.currentTimeMillis()
                val url = originalHttpUrl.newBuilder().
                    addQueryParameter("apikey", BuildConfig.apikey).
                    addQueryParameter("ts","$ts").
                    addQueryParameter("hash",geraHash(ts)).
                    build()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-type", "application/json")
                    .url(url)

                val request = requestBuilder?.build()
                it.proceed(request)
            }
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(headerInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelAPI::class.java)
        }
    }
}