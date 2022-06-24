package com.jaimeballesta.data.model.home

import com.google.gson.annotations.SerializedName

data class CharacterObjectModel(
    @SerializedName("data") val data: CharacterDataModel
)
