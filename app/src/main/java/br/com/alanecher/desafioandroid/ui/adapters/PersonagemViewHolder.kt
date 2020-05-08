package br.com.alanecher.desafioandroid.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alanecher.desafioandroid.R
import br.com.alanecher.desafioandroid.domain.Character
import com.squareup.picasso.Picasso

class PersonagemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var imagem: ImageView? = null
    var txtNome: TextView? = null
    var txtDescricao: TextView? = null

    init{
        imagem = itemView.findViewById(R.id.imagem)
        txtNome = itemView.findViewById(R.id.txtNome)
        txtDescricao = itemView.findViewById(R.id.txtDescricao)
    }

    fun bind(item:Character?, clickListener:OnItemClickListener){
        txtNome?.text = item!!.name
        txtDescricao?.text = item!!.description

        var picasso = Picasso.get()
        picasso
            .load(item.thumbnail!!.buildImageThumb())
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(imagem);

        itemView.setOnClickListener{
            clickListener.onItemClick(item)
        }
    }

    companion object{
        fun create(parent: ViewGroup): PersonagemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_personagens, parent, false)
            return PersonagemViewHolder(view)
        }
    }
}