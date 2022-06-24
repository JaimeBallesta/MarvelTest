package com.jaimeballesta.data.repository

import com.jaimeballesta.data.source.RemoteDataSource
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.domain.model.home.CharacterItem

class MarvelRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getCharacters(): List<CharacterItem> = remoteDataSource.getCharacters()

    suspend fun getCharacterDetails(characterId: String): List<CharacterDetailItem> =
        remoteDataSource.getDetailsCharacter(characterId)

}