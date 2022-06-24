package com.jaimeballesta.marveltest.data.network.sources

import com.jaimeballesta.data.mappers.toDomain
import com.jaimeballesta.data.source.RemoteDataSource
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.domain.model.home.CharacterItem
import com.jaimeballesta.marveltest.data.network.MarvelService

class RemoteDataSourceImpl(private val service: MarvelService) : RemoteDataSource {

    override suspend fun getCharacters(): List<CharacterItem> =
        service.getCharacters()?.let { characters ->
            characters.data.results.map { it.toDomain() }
        } ?: emptyList()

    override suspend fun getDetailsCharacter(characterId: String): List<CharacterDetailItem> =
        service.getDetailCharacter(characterId)?.let { detailObject ->
            detailObject.data.results.map { it.toDomain() }
    } ?: emptyList()


}