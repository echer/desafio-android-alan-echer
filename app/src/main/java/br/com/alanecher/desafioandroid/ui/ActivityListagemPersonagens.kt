package br.com.alanecher.desafioandroid.ui

import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.domain.Character
import br.com.alanecher.desafioandroid.ui.adapters.ListagemPersonagensAdapter
import br.com.alanecher.desafioandroid.ui.adapters.OnItemClickListener
import br.com.alanecher.desafioandroid.ui.adapters.EstadoPaginacao
import br.com.alanecher.desafioandroid.ui.viewmodels.ListagemPersonagensViewModel
import kotlinx.android.synthetic.main.activity_listagem_personagens.*


class ActivityListagemPersonagens : AppCompatActivity() {

    private val model: ListagemPersonagensViewModel by viewModels { ListagemPersonagensViewModel.ListagemPersonagemVMFactory }
    private lateinit var adapter: ListagemPersonagensAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_personagens)

        title = getString(R.string.title_listagem_personagens)

        Toast.makeText(this, getString(R.string.marvel_rights), Toast.LENGTH_SHORT).show()

        initAdapter()
        initState()
    }

    private fun initAdapter() {
        adapter = ListagemPersonagensAdapter({ model.retry() }, object : OnItemClickListener {
            override fun onItemClick(item: Any) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(
                        ActivityDetalhesPersonagem.newIntent(
                            this@ActivityListagemPersonagens,
                            item as Character
                        ), ActivityOptions.makeSceneTransitionAnimation(this@ActivityListagemPersonagens).toBundle()
                    )
                }else{
                    startActivity(
                        ActivityDetalhesPersonagem.newIntent(
                            this@ActivityListagemPersonagens,
                            item as Character
                        )
                    )
                }
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        model.personagensLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initState() {
        txt_error.setOnClickListener { model.retry() }
        model.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (model.listIsEmpty() && state == EstadoPaginacao.CARREGANDO) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (model.listIsEmpty() && state == EstadoPaginacao.ERRO) View.VISIBLE else View.GONE
            if (!model.listIsEmpty()) {
                adapter.setState(state ?: EstadoPaginacao.COMPLETO)
            }
        })
    }
}
