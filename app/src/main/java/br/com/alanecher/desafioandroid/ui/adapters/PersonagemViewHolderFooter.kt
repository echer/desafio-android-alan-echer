package br.com.alanecher.desafioandroid.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alanecher.desafioandroid.R
import kotlinx.android.synthetic.main.item_list_footer.view.*

class PersonagemViewHolderFooter(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: EstadoPaginacao?) {
        itemView.progress_bar.visibility = if (status == EstadoPaginacao.CARREGANDO) VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == EstadoPaginacao.ERRO) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): PersonagemViewHolderFooter {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return PersonagemViewHolderFooter(view)
        }
    }
}