package br.com.alanecher.desafioandroid.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.domain.Character


class ListagemPersonagensAdapter(private val personagens: List<Character>, private val clickListener:OnItemClickListener) : RecyclerView.Adapter<ListagemPersonagensAdapter.PersonagemViewHolder>() {

    inner class PersonagemViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {

        var imagem: ImageView? = null
        var txtNome: TextView? = null
        var txtDescricao: TextView? = null

        init{
            imagem = itemView.findViewById(R.id.imagem)
            txtNome = itemView.findViewById(R.id.txtNome)
            txtDescricao = itemView.findViewById(R.id.txtDescricao)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonagemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_personagens, parent, false)
        return PersonagemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personagens.size
    }

    override fun onBindViewHolder(holder: PersonagemViewHolder, position: Int) {
        var item = personagens[position]
        holder.txtNome?.text = item.name
        holder.txtDescricao?.text = item.description

        holder.itemView.setOnClickListener{
            clickListener.onItemClick(item)
        }
    }

}
