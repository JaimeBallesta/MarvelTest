package com.jaimeballesta.data.model

import com.google.gson.annotations.SerializedName

data class CharacterDataModel(
    @SerializedName("results") val results: List<CharacterModel> = emptyList()
)
