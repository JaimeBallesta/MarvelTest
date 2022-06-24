package com.jaimeballesta.data.model

import com.google.gson.annotations.SerializedName

data class CharacterObjectModel(
    @SerializedName("data") val data: CharacterDataModel
)
