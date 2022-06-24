package com.jaimeballesta.domain.model.detail

import com.jaimeballesta.domain.model.ThumbnailItem

data class CharacterDetailItem(
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailItem,
    val comics: DetailItemItem,
)