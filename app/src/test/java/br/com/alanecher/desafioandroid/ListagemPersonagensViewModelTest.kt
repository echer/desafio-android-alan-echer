package br.com.alanecher.desafioandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.adapters.EstadoPaginacao
import br.com.alanecher.desafioandroid.ui.viewmodels.paginating.ListaPersonagensDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class ListagemPersonagensViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    var mInitialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Character>? = null

    @Mock
    var mAfterCallback: PageKeyedDataSource.LoadCallback<Int, Character>? = null

    @Mock
    var observer: Observer<EstadoPaginacao>? = null

    private lateinit var datasource: ListaPersonagensDataSourceFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        var compositeDisposable = CompositeDisposable()
        datasource = ListaPersonagensDataSourceFactory(compositeDisposable, MarvelAPI.criaAPI())
        datasource.create()

        datasource.datasourceLiveData.value!!.estadoPaginacao!!.observeForever(observer!!)
    }

    @Test
    @Throws(Exception::class)
    fun testeDatasourceListagemInicialPageList() {

        val params: PageKeyedDataSource.LoadInitialParams<Int> =
            PageKeyedDataSource.LoadInitialParams<Int>(20, false)
        datasource.datasourceLiveData.value!!.loadInitial(params, mInitialCallback!!)

        verify(observer)!!.onChanged(EstadoPaginacao.COMPLETO)
    }

    @Test
    @Throws(Exception::class)
    fun testeDatasourceListagemProximaPaginaPageList() {

        val params: PageKeyedDataSource.LoadParams<Int> =
            PageKeyedDataSource.LoadParams<Int>(20, 20)
        datasource.datasourceLiveData.value!!.loadAfter(params, mAfterCallback!!)

        verify(observer)!!.onChanged(EstadoPaginacao.COMPLETO)
    }
}