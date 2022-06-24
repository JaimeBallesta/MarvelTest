package com.jaimeballesta.marveltest.di

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.data.source.RemoteDataSource
import com.jaimeballesta.marveltest.data.network.MarvelService
import com.jaimeballesta.marveltest.data.network.sources.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideRemoteDataSource(marvelService: MarvelService): RemoteDataSource =
        RemoteDataSourceImpl(marvelService)

    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource): MarvelRepository =
        MarvelRepository(remoteDataSource)

}