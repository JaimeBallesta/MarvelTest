package com.jaimeballesta.data.model

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel,
)
