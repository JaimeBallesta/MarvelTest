package com.jaimeballesta.data.mappers

import com.jaimeballesta.data.model.home.CharacterModel
import com.jaimeballesta.data.model.common.ThumbnailModel
import com.jaimeballesta.data.model.details.CharacterDetailModel
import com.jaimeballesta.data.model.details.CharacterSectionDetailModel
import com.jaimeballesta.data.model.details.DetailItemModel
import com.jaimeballesta.data.model.details.ItemModel
import com.jaimeballesta.domain.model.home.CharacterItem
import com.jaimeballesta.domain.model.ThumbnailItem
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.domain.model.detail.DetailItemItem
import com.jaimeballesta.domain.model.detail.ItemItem
import com.jaimeballesta.domain.model.detail.SectionDetailItem


fun CharacterModel.toDomain(): CharacterItem = CharacterItem(
    id = id.toString(), name = name, thumbnail = thumbnail.toDomain()
)

fun ThumbnailModel.toDomain(): ThumbnailItem = ThumbnailItem(
    path = path, extension = extension
)

fun CharacterDetailModel.toDomain(): CharacterDetailItem = CharacterDetailItem(
    id = id.toString(),
    name = name,
    description = description,
    thumbnail = thumbnail.toDomain(),
    comics = comics.toDomain(),
    series = series.toDomain(),
    stories = stories.toDomain(),
    events = events.toDomain()
)

fun DetailItemModel.toDomain(): DetailItemItem = DetailItemItem(
    items = items.map { it.toDomain() }
)

fun ItemModel.toDomain(): ItemItem = ItemItem(
    name = name, resourceURI = resourceURI
)

fun CharacterSectionDetailModel.toDomain(): SectionDetailItem = SectionDetailItem(
    id = id.toString(), title = title, description = description, thumbnail = thumbnail?.toDomain()
)