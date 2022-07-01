package com.jaimeballesta.usecases.mocks

import com.jaimeballesta.domain.model.ThumbnailItem
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.domain.model.detail.DetailItemItem
import com.jaimeballesta.domain.model.detail.SectionDetailItem
import com.jaimeballesta.domain.model.home.CharacterItem

const val sectionMock = "sectionMock"

val characterItemMock = CharacterItem(
    id = "12345",
    name = "ItemTest",
    thumbnail = ThumbnailItem()
)

val itemDetailMock = CharacterDetailItem(
    id = "12345",
    name = "ItemTest",
    description = "this is a mock",
    thumbnail = ThumbnailItem(),
    comics = DetailItemItem(emptyList()),
    series = DetailItemItem(emptyList()),
    stories = DetailItemItem(emptyList()),
    events = DetailItemItem(emptyList())
)

val sectionDetailItemMock = SectionDetailItem(
    id = "12345",
    title = "ItemTest",
    description = "this is a mock",
    thumbnail = ThumbnailItem()
)
