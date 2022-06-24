package com.jaimeballesta.domain.model.home

import com.jaimeballesta.domain.model.ThumbnailItem

data class CharacterItem(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailItem
    )