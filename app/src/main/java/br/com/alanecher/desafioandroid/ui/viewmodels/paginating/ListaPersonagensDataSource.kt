package br.com.alanecher.desafioandroid.ui.viewmodels.paginating

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.adapters.EstadoPaginacao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers


class ListaPersonagensDataSource(
    private val marvelAPI: MarvelAPI,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Character>() {

    private var tentarNovCompletable: Completable? = null
    var estadoPaginacao: MutableLiveData<EstadoPaginacao> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character?>
    ) {

        updateState(EstadoPaginacao.CARREGANDO)
        compositeDisposable.add(
            marvelAPI.listaPersonagens(0, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(EstadoPaginacao.COMPLETO)
                        callback.onResult(
                            response!!.data!!.results!!,
                            null,
                            params.requestedLoadSize
                        )
                    },
                    {
                        updateState(EstadoPaginacao.ERRO)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Character>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Character>
    ) {
        updateState(EstadoPaginacao.CARREGANDO)
        compositeDisposable.add(
            marvelAPI.listaPersonagens(params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(EstadoPaginacao.COMPLETO)
                        callback.onResult(
                            response!!.data!!.results!!,
                            params.key + params.requestedLoadSize
                        )
                    },
                    {
                        updateState(EstadoPaginacao.ERRO)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    private fun updateState(estadoPaginacao: EstadoPaginacao) {
        this.estadoPaginacao.postValue(estadoPaginacao)
    }

    fun retry() {
        if (tentarNovCompletable != null) {
            compositeDisposable.add(
                tentarNovCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        tentarNovCompletable = if (action == null) null else Completable.fromAction(action)
    }
}