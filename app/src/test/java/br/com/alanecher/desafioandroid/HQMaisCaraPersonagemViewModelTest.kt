package br.com.alanecher.desafioandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.domain.Comic
import br.com.alanecher.desafioandroid.mocks.MockReader
import br.com.alanecher.desafioandroid.ui.viewmodels.HQMaisCaraPersonagemViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class HQMaisCaraPersonagemViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HQMaisCaraPersonagemViewModel

    @Mock
    lateinit var observer: Observer<Comic>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = HQMaisCaraPersonagemViewModel.HQMaisCaraPersonagemVMFactory.create(HQMaisCaraPersonagemViewModel::class.java)
    }

    @Test
    fun testeHQMaisCara() {

        var mock = MockReader<Character>().getMock("mock-personagem", Character::class.java)
        viewModel.carregarHQMaisCara(mock.id.toString())

        var mockHQ = MockReader<Comic>().getMock("mock-hq", Comic::class.java)

        assertNotNull(viewModel.quadrinhoMaisCaro.getOrAwaitValue())

        assertEquals(mockHQ.id, viewModel.quadrinhoMaisCaro.value!!.id)
        assertEquals(viewModel.encontraHQMaisCara(arrayListOf(mockHQ))!!.priceMax!!.price, viewModel.quadrinhoMaisCaro.value!!.priceMax!!.price)
    }

}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}