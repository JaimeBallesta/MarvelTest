package com.jaimeballesta.marveltest.data.network

import com.jaimeballesta.data.model.CharacterObjectModel
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApiClient {

    @GET("v1/public/characters")
    suspend fun getCharacters(): Response<CharacterObjectModel>

}