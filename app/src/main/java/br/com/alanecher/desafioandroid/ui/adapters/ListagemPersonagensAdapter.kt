package br.com.alanecher.desafioandroid.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.alanecher.desafioandroid.domain.Character


class ListagemPersonagensAdapter(private val retry: () -> Unit, private val clickListener:OnItemClickListener)
    : PagedListAdapter<Character, RecyclerView.ViewHolder>(PersonagensDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = EstadoPaginacao.CARREGANDO

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) PersonagemViewHolder.create(parent) else PersonagemViewHolderFooter.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as PersonagemViewHolder).bind(getItem(position), clickListener)
        else (holder as PersonagemViewHolderFooter).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == EstadoPaginacao.CARREGANDO || state == EstadoPaginacao.ERRO)
    }

    fun setState(estadoPaginacao: EstadoPaginacao) {
        this.state = estadoPaginacao
        notifyItemChanged(super.getItemCount())
    }

    companion object {
        val PersonagensDiffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

}
