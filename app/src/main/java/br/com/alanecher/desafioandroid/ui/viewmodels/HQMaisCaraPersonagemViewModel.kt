package br.com.alanecher.desafioandroid.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.domain.Comic
import br.com.alanecher.desafioandroid.domain.ComicDataWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HQMaisCaraPersonagemViewModel(
    private var api: MarvelAPI
) : ViewModel() {

    private var pageSize = 20
    private var offset = 0
    var errorAPILiveData = MutableLiveData<ComicDataWrapper>()
    private val quadrinhosLiveData = MutableLiveData<Comic>()
    private var quadrinhosPaginado = ArrayList<Comic>()
    val quadrinhoMaisCaro: LiveData<Comic>
        get() = quadrinhosLiveData

    fun carregarHQMaisCara(id: String) {
        carregarHQs(offset, pageSize, id, quadrinhosPaginado)
    }

    private fun carregarHQs(
        offset: Int,
        pageSize: Int,
        id: String,
        quadrinhosPaginado: ArrayList<Comic>
    ) {
        api.listaHQPorPersonagem(id, offset, pageSize).enqueue(
            object : Callback<ComicDataWrapper> {
                override fun onFailure(call: Call<ComicDataWrapper>, t: Throwable) {
                    errorAPILiveData.value =
                        ComicDataWrapper(0, t.message, null, null, null, null, null)
                }

                override fun onResponse(
                    call: Call<ComicDataWrapper>,
                    response: Response<ComicDataWrapper>
                ) {

                    when (response.code()) {
                        200 -> {
                            quadrinhosPaginado.addAll(response.body()?.data?.results!!)
                            if (quadrinhosPaginado.size < response.body()?.data?.total!! && response.body()?.data?.results?.size!! > 0) {
                                Thread.sleep(100)
                                api = MarvelAPI.criaAPI()
                                carregarHQs(offset + pageSize, pageSize, id, quadrinhosPaginado)
                            } else {
                                quadrinhosLiveData.value = encontraHQMaisCara(quadrinhosPaginado)
                            }
                        }
                        else -> {
                            errorAPILiveData.value = response.body()
                        }
                    }

                }
            }
        )
    }

    fun encontraHQMaisCara(list: ArrayList<Comic>): Comic? {
        var hqMaisCara = list.map { comic ->
            var priceMax = comic.prices!!.maxBy { price ->
                price.price!!
            }
            if (comic.priceMax == null || priceMax!!.price!! > comic.priceMax!!.price!!) {
                comic.priceMax = priceMax
            }
            comic
        }.maxBy {
            it!!.priceMax!!.price!!
        }
        return hqMaisCara
    }

    object HQMaisCaraPersonagemVMFactory : ViewModelProvider.Factory {

        private val api by lazy {
            MarvelAPI.criaAPI()
        }

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HQMaisCaraPersonagemViewModel(
                api
            ) as T
        }
    }

}