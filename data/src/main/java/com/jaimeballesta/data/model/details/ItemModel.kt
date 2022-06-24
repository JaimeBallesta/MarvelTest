package com.jaimeballesta.data.model.details

import com.google.gson.annotations.SerializedName

data class ItemModel(
    @SerializedName("name") val name: String,
    @SerializedName("resourceURI") val resourceURI: String
)
