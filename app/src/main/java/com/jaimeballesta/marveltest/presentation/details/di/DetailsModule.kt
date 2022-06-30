package com.jaimeballesta.marveltest.presentation.details.di

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.usecases.GetCharacterDetailsUseCase
import com.jaimeballesta.usecases.GetSectionDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailsModule {

    @Provides
    @ViewModelScoped
    fun provideGetCharacterDetailUseCase(repository: MarvelRepository): GetCharacterDetailsUseCase =
        GetCharacterDetailsUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetSectionDetailsUseCase(repository: MarvelRepository): GetSectionDetailsUseCase =
        GetSectionDetailsUseCase(repository)
}