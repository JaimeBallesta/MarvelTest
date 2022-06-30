package com.jaimeballesta.marveltest.data.network

import com.jaimeballesta.data.model.details.CharacterDetailObjectModel
import com.jaimeballesta.data.model.details.CharacterSectionDetailObjectModel
import com.jaimeballesta.data.model.home.CharacterObjectModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelService @Inject constructor(private val apiClient: MarvelApiClient) {

    suspend fun getCharacters(): CharacterObjectModel? = withContext(Dispatchers.IO) {
        apiClient.getCharacters().body()
    }

    suspend fun getDetailCharacter(characterId: String): CharacterDetailObjectModel? =
        withContext(Dispatchers.IO) {
            apiClient.getCharacterDetails(characterId).body()
        }

    suspend fun getDetailSection(section: String, characterId: String): CharacterSectionDetailObjectModel? =
        withContext(Dispatchers.IO) {
            apiClient.getCharacterDetailSection(section, characterId).body()
        }
}