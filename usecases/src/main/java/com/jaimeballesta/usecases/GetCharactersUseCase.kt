package com.jaimeballesta.usecases

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.domain.common.connectionErrorCode
import com.jaimeballesta.domain.model.home.CharacterItem
import com.jaimeballesta.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCharactersUseCase(private val repository: MarvelRepository) {
    operator fun invoke(): Flow<Resource<List<CharacterItem>>> = flow {
        try {
            emit(Resource.Loading(emptyList()))
            emit(Resource.Success(repository.getCharacters()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString(), emptyList<CharacterItem>()))
        } catch (e: IOException) {
            emit(Resource.Error(connectionErrorCode.toString(), emptyList<CharacterItem>()))
        }
    }
}