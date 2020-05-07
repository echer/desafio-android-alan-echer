package br.com.alanecher.desafioandroid.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.domain.CharacterDataWrapper
import br.com.alanecher.desafioandroid.ui.ActivityListagemPersonagens
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListagemPersonagensViewModel(
    private val api: MarvelAPI
) : ViewModel() {

    private val personagensLiveData = MutableLiveData<List<Character>>()
    val personagensList: LiveData<List<Character>>
        get() = personagensLiveData

    fun listarPersonagens() {
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

                            personagensLiveData.value = response.body()?.data?.results

                            /*api.listaHQPorPersonagem(response.body()?.data?.results?.get(0)?.id.toString()).enqueue(
                                object : Callback<ComicDataWrapper> {
                                    override fun onFailure(call: Call<ComicDataWrapper>, t: Throwable) {
                                        Log.e(ActivityListagemPersonagens::class.java.simpleName, "Listagem erro!")
                                    }

                                    override fun onResponse(
                                        call: Call<ComicDataWrapper>,
                                        response: Response<ComicDataWrapper>
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
                            )*/

                        }
                        else -> {
                            personagensLiveData.value = ArrayList()
                        }
                    }

                }
            }
        )
    }

    object ListagemPersonagemVMFactory : ViewModelProvider.Factory {

        private val api by lazy {
            MarvelAPI.criaAPI()
        }

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListagemPersonagensViewModel(
                api
            ) as T
        }
    }

}