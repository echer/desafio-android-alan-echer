package br.com.alanecher.desafioandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.viewmodels.DetalhesPersonagemViewModel
import kotlinx.android.synthetic.main.activity_detalhes_personagem.*

class ActivityDetalhesPersonagem : AppCompatActivity() {

    private val model: DetalhesPersonagemViewModel by viewModels { DetalhesPersonagemViewModel.DetalhesPersonagemVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_personagem)

        if (!intent.hasExtra(EXTRA_PERSONAGEM)) {
            finish()
            return
        }

        btnHQMaisCara.setOnClickListener {

        }

        model.personagem.observe(this, Observer {
            txtNome.text = it.name
            txtDescricao.text = it.description
        })

        model.carregaPersonagem(intent.getSerializableExtra(EXTRA_PERSONAGEM) as Character)

    }

    companion object {
        const val EXTRA_PERSONAGEM = "EXTRA_PERSONAGEM"
        fun newIntent(context: Context, personagem: Character): Intent {
            var intent = Intent(context, ActivityDetalhesPersonagem::class.java)
            intent.putExtra(EXTRA_PERSONAGEM, personagem)
            return intent
        }
    }
}
