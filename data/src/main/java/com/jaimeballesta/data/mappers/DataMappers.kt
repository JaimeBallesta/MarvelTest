package com.jaimeballesta.data.mappers

import com.jaimeballesta.data.model.CharacterModel
import com.jaimeballesta.data.model.ThumbnailModel
import com.jaimeballesta.domain.model.CharacterItem
import com.jaimeballesta.domain.model.ThumbnailItem


fun CharacterModel.toDomain(): CharacterItem = CharacterItem(
    id = id, name = name, thumbnail = thumbnail.toDomain()
)

fun ThumbnailModel.toDomain(): ThumbnailItem = ThumbnailItem(
    path = path, extension = extension
)
