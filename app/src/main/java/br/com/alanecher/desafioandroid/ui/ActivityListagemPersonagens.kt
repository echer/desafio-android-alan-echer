package br.com.alanecher.desafioandroid.ui

import android.content.ClipData.newIntent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.adapters.ListagemPersonagensAdapter
import br.com.alanecher.desafioandroid.ui.adapters.OnItemClickListener
import br.com.alanecher.desafioandroid.ui.viewmodels.ListagemPersonagensViewModel
import kotlinx.android.synthetic.main.activity_listagem_personagens.*


class ActivityListagemPersonagens : AppCompatActivity() {

    private val model: ListagemPersonagensViewModel by viewModels { ListagemPersonagensViewModel.ListagemPersonagemVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_personagens)

        Toast.makeText(this,getString(R.string.marvel_rights), Toast.LENGTH_SHORT).show()

        model.personagensList.observe(this,
            Observer {
                var recyclerViewAdapter = ListagemPersonagensAdapter(it, object : OnItemClickListener{
                    override fun onItemClick(item: Any) {
                        startActivity(ActivityDetalhesPersonagem.newIntent(this@ActivityListagemPersonagens,item as Character))
                    }
                })
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = recyclerViewAdapter
            })

        model.listarPersonagens()
    }
}
