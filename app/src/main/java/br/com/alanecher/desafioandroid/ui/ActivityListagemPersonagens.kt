package br.com.alanecher.desafioandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.api.dto.CharacterDataWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityListagemPersonagens : AppCompatActivity() {

    private val api by lazy {
        MarvelAPI.criaAPI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_personagens)

        api.listaPersonagens().enqueue(
            object : Callback<CharacterDataWrapper> {
                override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                    Log.e(ActivityListagemPersonagens::class.java.simpleName, "Listagem erro!")
                }

                override fun onResponse(
                    call: Call<CharacterDataWrapper>,
                    response: Response<CharacterDataWrapper>
                ) {

                    when (response.code()) {
                        200 -> {
                            Log.i(
                                ActivityListagemPersonagens::class.java.simpleName,
                                "Listagem sucesso!"
                            )

                        }
                        else -> {

                        }
                    }

                }
            }
        )
    }
}
