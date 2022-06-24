package com.jaimeballesta.marveltest.data.network

import com.jaimeballesta.data.model.details.CharacterDetailObjectModel
import com.jaimeballesta.data.model.home.CharacterObjectModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiClient {

    @GET("v1/public/characters")
    suspend fun getCharacters(): Response<CharacterObjectModel>

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: String,
    ): Response<CharacterDetailObjectModel>
}