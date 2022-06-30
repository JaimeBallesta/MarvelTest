package com.jaimeballesta.marveltest.presentation.details.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaimeballesta.domain.model.detail.SectionDetailItem
import com.jaimeballesta.marveltest.R
import com.jaimeballesta.marveltest.databinding.ItemViewBinding
import com.jaimeballesta.marveltest.presentation.common.buildImageUrl
import com.jaimeballesta.marveltest.presentation.common.inflate
import com.jaimeballesta.marveltest.presentation.common.loadUrlRounded

class ItemAdapter() :
    ListAdapter<SectionDetailItem, ItemAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_view, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemViewBinding.bind(itemView)
        fun bind(item: SectionDetailItem) = with(binding) {
            item.apply {
                this@with.name.text = title
                this@with.logoImageView.loadUrlRounded(buildImageUrl(thumbnail?.path, thumbnail?.extension))
            }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<SectionDetailItem>() {
        override fun areItemsTheSame(oldItem: SectionDetailItem, newItem: SectionDetailItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SectionDetailItem, newItem: SectionDetailItem): Boolean =
            oldItem == newItem

    }
}

