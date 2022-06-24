package com.jaimeballesta.marveltest.data.network

import com.jaimeballesta.data.model.CharacterObjectModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelService @Inject constructor(private val apiClient: MarvelApiClient) {

    suspend fun getCharacters(): CharacterObjectModel? = withContext(Dispatchers.IO) {
        apiClient.getCharacters().body()
    }

}