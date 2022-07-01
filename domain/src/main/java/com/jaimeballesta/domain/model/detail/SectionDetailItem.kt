package com.jaimeballesta.domain.model.detail

import com.jaimeballesta.domain.common.EMPTY_DESCRIPTION
import com.jaimeballesta.domain.model.ThumbnailItem

data class SectionDetailItem(
    val id: String,
    val title: String,
    val description: String? = EMPTY_DESCRIPTION,
    val thumbnail: ThumbnailItem? = ThumbnailItem()
)