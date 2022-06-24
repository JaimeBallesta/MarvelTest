package com.jaimeballesta.marveltest.presentation.home.di

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.usecases.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {

    @Provides
    @ViewModelScoped
    fun provideGetCharactersUseCase(repository: MarvelRepository): GetCharactersUseCase =
        GetCharactersUseCase(repository)
}