package com.jaimeballesta.data.source

import com.jaimeballesta.domain.model.CharacterItem

interface RemoteDataSource {
    suspend fun getCharacters() : List<CharacterItem>
}