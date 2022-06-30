package com.jaimeballesta.domain.model.detail

import com.jaimeballesta.domain.common.EMPTY_STRING
import com.jaimeballesta.domain.model.ThumbnailItem

data class SectionDetailItem(
    val id: String,
    val title: String,
    val description: String? = EMPTY_STRING,
    val thumbnail: ThumbnailItem? = ThumbnailItem()
)