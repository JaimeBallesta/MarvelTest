package com.jaimeballesta.usecases.details

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.usecases.CoroutinesTestRule
import com.jaimeballesta.usecases.mocks.itemDetailMock
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
class GetCharacterDetailsUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var repository: MarvelRepository

    private lateinit var getCharDetailsUseCase: GetCharacterDetailsUseCase

    @Before
    fun setUp() {
        getCharDetailsUseCase = GetCharacterDetailsUseCase(repository)
    }

    @Test
    fun `invoke must call list charactersDetails from repository`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = listOf(itemDetailMock)
            whenever(repository.getCharacterDetails(itemDetailMock.id)).thenReturn(resultExpected)

            val result: Flow<Resource<List<CharacterDetailItem>>> = getCharDetailsUseCase(
                itemDetailMock.id)

            repository.getCharacterDetails(itemDetailMock.id)

            verify(repository).getCharacterDetails(itemDetailMock.id)

            result.collect {
                if (it is Resource.Success)
                    Assert.assertEquals(resultExpected, it.data)
            }
        }
}