package br.com.alanecher.desafioandroid.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.viewmodels.HQMaisCaraPersonagemViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_h_q_mais_cara_personagem.*

class ActivityHQMaisCaraPersonagem : AppCompatActivity() {

    private val model: HQMaisCaraPersonagemViewModel by viewModels { HQMaisCaraPersonagemViewModel.HQMaisCaraPersonagemVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                exitTransition = Explode()
                enterTransition = Explode()
            }
        }
        setContentView(R.layout.activity_h_q_mais_cara_personagem)

        if (!intent.hasExtra(EXTRA_PERSONAGEM)) {
            finish()
            return
        }

        model.quadrinhoMaisCaro.observe(this, Observer {
            if (it != null) {

                txtTitulo.text = it.title
                txtDescricao.text = it.description
                txtPreco.text = "$ ${it.priceMax!!.price}"
                title = getString(R.string.title_actv_hqmaiscara) + it.title
                var picasso = Picasso.get()
                //picasso.isLoggingEnabled = true
                picasso
                    .load(it.thumbnail!!.buildImageDetail())
                    //.resize(216, 324)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgHQ)
            } else {
                msgFinish(getString(R.string.vld_personagem_sem_quadrinho))
            }

            progressBar1.visibility = View.GONE
        })
        model.errorAPILiveData.observe(this, Observer {
            progressBar1.visibility = View.GONE
            if(it != null)
                msgFinish("${it.code} - ${it.status}")
            else
                msgFinish(getString(R.string.msg_erro))
        })

        progressBar1.visibility = View.VISIBLE
        model.carregarHQMaisCara((intent.getSerializableExtra(EXTRA_PERSONAGEM) as Character).id.toString())
    }

    fun msgFinish(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_atencao))
        builder.setMessage(message)
        builder.setPositiveButton(
            getString(R.string.msg_ok),
            DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
            })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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
