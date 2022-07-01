package com.jaimeballesta.usecases.home

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.model.home.CharacterItem
import com.jaimeballesta.usecases.CoroutinesTestRule
import com.jaimeballesta.usecases.mocks.characterItemMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var repository: MarvelRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setUp(){
        getCharactersUseCase = GetCharactersUseCase(repository)
    }

    @Test
    fun `invoke must call list characters from repository`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = listOf(characterItemMock, characterItemMock.copy(id = "54321"))
            whenever(repository.getCharacters()).thenReturn(resultExpected)

            val result: Flow<Resource<List<CharacterItem>>> = getCharactersUseCase()

            repository.getCharacters()

            verify(repository).getCharacters()

            result.collect {
                if (it is Resource.Success)
                    Assert.assertEquals(resultExpected, it.data)
            }
        }
}