package com.jaimeballesta.data.model.details

import com.google.gson.annotations.SerializedName

data class DetailItemModel(
    @SerializedName("items") val items: List<ItemModel>
)
