package br.com.alanecher.desafioandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.mocks.MockReader
import br.com.alanecher.desafioandroid.ui.viewmodels.DetalhesPersonagemViewModel
import com.google.gson.Gson
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.*


class DetalhesPersonagemViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetalhesPersonagemViewModel

    @Mock
    lateinit var observer: Observer<Character>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetalhesPersonagemViewModel.DetalhesPersonagemVMFactory.create(DetalhesPersonagemViewModel::class.java)
        viewModel.personagem.observeForever(observer)
    }

    @Test
    fun testeCarregaPersonagem() {
        var mock = MockReader<Character>().getMock("mock-personagem", Character::class.java)
        viewModel.carregaPersonagem(mock)

        verify(observer).onChanged(mock)
    }
}