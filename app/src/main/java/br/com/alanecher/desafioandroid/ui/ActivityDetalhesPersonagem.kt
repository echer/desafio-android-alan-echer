package br.com.alanecher.desafioandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.viewmodels.DetalhesPersonagemViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhes_personagem.*
import kotlinx.android.synthetic.main.activity_detalhes_personagem.progressBar1
import kotlinx.android.synthetic.main.activity_listagem_personagens.*

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
            startActivity(ActivityHQMaisCaraPersonagem.newIntent(ActivityDetalhesPersonagem@this,model.personagem.value))
        }

        model.personagem.observe(this, Observer {
            txtNome.text = it.name
            txtDescricao.text = it.description
            title = getString(R.string.title_actv_detalhes)+it.name
            var picasso = Picasso.get()
            //picasso.isLoggingEnabled = true
            picasso
                .load(it.thumbnail!!.buildImageDetail())
                //.resize(216, 324)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imgDetalhe)

            progressBar1.visibility = View.GONE
        })

        progressBar1.visibility = View.VISIBLE
        model.carregaPersonagem(intent.getSerializableExtra(EXTRA_PERSONAGEM) as Character)

    }

    companion object {
        const val EXTRA_PERSONAGEM = "EXTRA_PERSONAGEM"
        //TODO MELHORAR NAVEGACAO PARA O JETPACK
        fun newIntent(context: Context, personagem: Character): Intent {
            var intent = Intent(context, ActivityDetalhesPersonagem::class.java)
            intent.putExtra(EXTRA_PERSONAGEM, personagem)
            return intent
        }
    }
}
