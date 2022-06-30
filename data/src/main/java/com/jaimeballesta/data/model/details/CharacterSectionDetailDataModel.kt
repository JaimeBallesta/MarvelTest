package com.jaimeballesta.data.model.details

import com.google.gson.annotations.SerializedName

data class CharacterSectionDetailDataModel(
    @SerializedName("results") val results: List<CharacterSectionDetailModel> = emptyList()
)
