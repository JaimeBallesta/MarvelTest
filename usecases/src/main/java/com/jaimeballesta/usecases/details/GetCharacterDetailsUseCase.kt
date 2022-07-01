package com.jaimeballesta.usecases.details

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.domain.common.connectionErrorCode
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCharacterDetailsUseCase(private val repository: MarvelRepository) {
    operator fun invoke(characterId: String): Flow<Resource<List<CharacterDetailItem>>> = flow {
        try {
            emit(Resource.Loading(emptyList()))
            emit(Resource.Success(repository.getCharacterDetails(characterId)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString(), emptyList<CharacterDetailItem>()))
        } catch (e: IOException) {
            emit(Resource.Error(connectionErrorCode.toString(), emptyList<CharacterDetailItem>()))
        }
    }
}