package com.jaimeballesta.data.model.home

import com.google.gson.annotations.SerializedName
import com.jaimeballesta.data.model.common.ThumbnailModel

data class CharacterModel(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel,
)
