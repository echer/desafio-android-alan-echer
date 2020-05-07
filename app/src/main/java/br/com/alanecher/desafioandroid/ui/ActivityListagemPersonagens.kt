package br.com.alanecher.desafioandroid.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.ui.adapters.ListagemPersonagensAdapter
import br.com.alanecher.desafioandroid.ui.viewmodels.ListagemPersonagensViewModel
import kotlinx.android.synthetic.main.activity_listagem_personagens.*


class ActivityListagemPersonagens : AppCompatActivity() {

    private val model: ListagemPersonagensViewModel by viewModels { ListagemPersonagensViewModel.ListagemPersonagemVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_personagens)

        model.personagensList.observe(this,
            Observer {
                var recyclerViewAdapter = ListagemPersonagensAdapter(it)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = recyclerViewAdapter
            })

        model.listarPersonagens()
    }
}
