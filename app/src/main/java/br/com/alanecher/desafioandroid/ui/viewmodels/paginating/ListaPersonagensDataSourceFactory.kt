package br.com.alanecher.desafioandroid.ui.viewmodels.paginating

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.domain.Character
import io.reactivex.disposables.CompositeDisposable

class ListaPersonagensDataSourceFactory (
    private val compositeDisposable: CompositeDisposable,
    private val marvelAPI: MarvelAPI)
    : DataSource.Factory<Int, Character>() {

    val datasourceLiveData = MutableLiveData<ListaPersonagensDataSource>()

    override fun create(): DataSource<Int, Character> {
        val datasource = ListaPersonagensDataSource(marvelAPI, compositeDisposable)
        datasourceLiveData.postValue(datasource)
        return datasource
    }
}