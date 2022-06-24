package com.jaimeballesta.data.model.home

import com.google.gson.annotations.SerializedName

data class CharacterDataModel(
    @SerializedName("results") val results: List<CharacterModel> = emptyList()
)
