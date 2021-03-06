package br.com.alanecher.desafioandroid.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alanecher.desafioandroid.api.MarvelAPI
import br.com.alanecher.desafioandroid.domain.Character

class DetalhesPersonagemViewModel(
) : ViewModel() {

    private val personagemLiveData = MutableLiveData<Character>()
    val personagem: LiveData<Character>
        get() = personagemLiveData

    fun carregaPersonagem(personagem: Character) {
        personagemLiveData.value = personagem
    }

    object DetalhesPersonagemVMFactory : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetalhesPersonagemViewModel() as T
        }
    }

}