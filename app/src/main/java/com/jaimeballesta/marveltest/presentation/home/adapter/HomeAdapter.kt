package com.jaimeballesta.marveltest.presentation.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaimeballesta.domain.model.home.CharacterItem
import com.jaimeballesta.marveltest.R
import com.jaimeballesta.marveltest.databinding.ItemViewChaptersBinding
import com.jaimeballesta.marveltest.presentation.common.buildImageUrl
import com.jaimeballesta.marveltest.presentation.common.inflate
import com.jaimeballesta.marveltest.presentation.common.loadUrl

class HomeAdapter(private val listener: (CharacterItem) -> Unit) :
    ListAdapter<CharacterItem, HomeAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_view_chapters, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    inner class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemViewChaptersBinding.bind(itemView)
        fun bind(item: CharacterItem) = with(binding) {
            item.apply {
                nameChapter.text = name
                imageChapter.loadUrl(buildImageUrl(thumbnail.path, thumbnail.extension))
            }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<CharacterItem>() {
        override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean =
            oldItem == newItem

    }
}

