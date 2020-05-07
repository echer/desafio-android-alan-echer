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
import br.com.alanecher.desafioandroid.ui.viewmodels.HQMaisCaraPersonagemViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_h_q_mais_cara_personagem.*
import kotlinx.android.synthetic.main.activity_h_q_mais_cara_personagem.progressBar1
import kotlinx.android.synthetic.main.activity_listagem_personagens.*

class ActivityHQMaisCaraPersonagem : AppCompatActivity() {

    private val model: HQMaisCaraPersonagemViewModel by viewModels { HQMaisCaraPersonagemViewModel.HQMaisCaraPersonagemVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_q_mais_cara_personagem)

        if (!intent.hasExtra(EXTRA_PERSONAGEM)) {
            finish()
            return
        }

        model.quadrinhoMaisCaro.observe(this, Observer {
            txtTitulo.text = it.title
            txtDescricao.text = it.description
            txtPreco.text = "$ ${it.priceMax!!.price}"
            title = getString(R.string.title_actv_hqmaiscara)+it.title
            var picasso = Picasso.get()
            //picasso.isLoggingEnabled = true
            picasso
                .load(it.thumbnail!!.buildImageDetail())
                //.resize(216, 324)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imgHQ)

            progressBar1.visibility = View.GONE
        })

        progressBar1.visibility = View.VISIBLE
        model.carregarHQMaisCara((intent.getSerializableExtra(EXTRA_PERSONAGEM) as Character).id.toString())
    }

    companion object {
        const val EXTRA_PERSONAGEM = "EXTRA_PERSONAGEM"

        //TODO MELHORAR NAVEGACAO PARA O JETPACK
        fun newIntent(context: Context, personagem: Character?): Intent {
            var intent = Intent(context, ActivityHQMaisCaraPersonagem::class.java)
            intent.putExtra(EXTRA_PERSONAGEM, personagem)
            return intent
        }
    }
}
