package com.jaimeballesta.domain.model

import com.jaimeballesta.domain.common.EMPTY_STRING

data class ThumbnailItem(
    val path: String? = EMPTY_STRING,
    val extension: String? = EMPTY_STRING
)
