package com.jaimeballesta.data.model.details

import com.google.gson.annotations.SerializedName
import com.jaimeballesta.data.model.common.ThumbnailModel

data class CharacterSectionDetailModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel?
)
