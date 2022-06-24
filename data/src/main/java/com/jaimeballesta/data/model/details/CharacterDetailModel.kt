package com.jaimeballesta.data.model.details

import com.google.gson.annotations.SerializedName
import com.jaimeballesta.data.model.common.ThumbnailModel

data class CharacterDetailModel(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel,
    @SerializedName("comics") val comics: DetailItemModel,
)
