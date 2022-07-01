package com.jaimeballesta.marveltest.presentation.home.viewModel

import app.cash.turbine.test
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.common.notCharactersErrorCode
import com.jaimeballesta.domain.common.unknownErrorCode
import com.jaimeballesta.domain.model.home.CharacterItem
import com.jaimeballesta.marveltest.CoroutinesTestRule
import com.jaimeballesta.marveltest.mocks.characterItemMock
import com.jaimeballesta.marveltest.presentation.home.state.HomeState
import com.jaimeballesta.usecases.home.GetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setUp(){
        homeViewModel = HomeViewModel(getCharactersUseCase)
    }

    @Test
    fun `getCharacters listen Flows emits a list of characters and change the UI state to LoadCharacters`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(listOf(characterItemMock, characterItemMock.copy(id = "54321")))

            whenever(getCharactersUseCase.invoke()).thenReturn(flowOf(resultExpected))

            homeViewModel.getCharacters()

            verify(getCharactersUseCase).invoke()

            getCharactersUseCase().collect {
                if (it is Resource.Success)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            homeViewModel.state.test {
                Assert.assertEquals(HomeState.LoadCharacters(resultExpected.data!!),
                    expectMostRecentItem())
            }
        }
    }

    @Test
    fun `getCharacters listen Flows emits a emptyList and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(emptyList<CharacterItem>())

            whenever(getCharactersUseCase.invoke()).thenReturn(flowOf(resultExpected))

            homeViewModel.getCharacters()

            verify(getCharactersUseCase).invoke()

            getCharactersUseCase().collect {
                if (it is Resource.Error)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            homeViewModel.state.test {
                Assert.assertEquals(HomeState.Error(notCharactersErrorCode.toString()),
                    expectMostRecentItem())
            }
        }
    }

    @Test
    fun `getCharacters listen Flows emits a null and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Error<List<CharacterItem>>(unknownErrorCode.toString(), null)

            whenever(getCharactersUseCase.invoke()).thenReturn(flowOf(resultExpected))

            homeViewModel.getCharacters()

            verify(getCharactersUseCase).invoke()

            getCharactersUseCase().collect {
                if (it is Resource.Error)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            homeViewModel.state.test {
                Assert.assertEquals(HomeState.Error(unknownErrorCode.toString()),
                    expectMostRecentItem())
            }
        }
    }
}