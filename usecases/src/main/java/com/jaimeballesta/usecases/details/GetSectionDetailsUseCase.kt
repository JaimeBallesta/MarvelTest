package com.jaimeballesta.usecases.details

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.domain.common.connectionErrorCode
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.model.detail.SectionDetailItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetSectionDetailsUseCase(private val repository: MarvelRepository) {
    operator fun invoke(
        section: String,
        characterId: String,
    ): Flow<Resource<List<SectionDetailItem>>> = flow {
        try {
            emit(Resource.Loading(emptyList()))
            emit(Resource.Success(repository.getDetailSection(section, characterId)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString(), emptyList<SectionDetailItem>()))
        } catch (e: IOException) {
            emit(Resource.Error(connectionErrorCode.toString(), emptyList<SectionDetailItem>()))
        }
    }
}