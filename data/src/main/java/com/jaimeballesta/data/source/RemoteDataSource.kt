package com.jaimeballesta.data.source

import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.domain.model.detail.SectionDetailItem
import com.jaimeballesta.domain.model.home.CharacterItem

interface RemoteDataSource {

    suspend fun getCharacters(): List<CharacterItem>

    suspend fun getDetailsCharacter(characterId: String): List<CharacterDetailItem>

    suspend fun getDetailSection( section: String, characterId: String): List<SectionDetailItem>
}