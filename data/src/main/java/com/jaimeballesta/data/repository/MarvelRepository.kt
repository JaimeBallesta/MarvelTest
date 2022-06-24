package com.jaimeballesta.data.repository

import com.jaimeballesta.data.source.RemoteDataSource
import com.jaimeballesta.domain.model.CharacterItem

class MarvelRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getCharacters(): List<CharacterItem> = remoteDataSource.getCharacters()

}