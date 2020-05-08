package br.com.alanecher.desafioandroid.ui.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.adapters.EstadoPaginacao
import br.com.alanecher.desafioandroid.ui.viewmodels.paginating.ListaPersonagensDataSource
import br.com.alanecher.desafioandroid.ui.viewmodels.paginating.ListaPersonagensDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class ListagemPersonagensViewModel(
    private val datasource:ListaPersonagensDataSourceFactory,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val pageSize = 20
    var personagensLiveData: LiveData<PagedList<Character>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        personagensLiveData = LivePagedListBuilder(datasource, config).build()
    }

    object ListagemPersonagemVMFactory : ViewModelProvider.Factory {

        private val api by lazy {
            MarvelAPI.criaAPI()
        }

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            var compositeDisposable = CompositeDisposable()
            var datasource = ListaPersonagensDataSourceFactory(compositeDisposable, api)
            return ListagemPersonagensViewModel(
                datasource,
                compositeDisposable
            ) as T
        }
    }

    fun getState(): LiveData<EstadoPaginacao> = Transformations.switchMap(datasource.datasourceLiveData, ListaPersonagensDataSource::estadoPaginacao)

    fun retry() {
        datasource.datasourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return personagensLiveData.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}