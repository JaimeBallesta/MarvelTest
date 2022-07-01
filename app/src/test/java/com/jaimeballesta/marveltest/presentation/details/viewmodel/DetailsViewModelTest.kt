package com.jaimeballesta.marveltest.presentation.details.viewmodel

import app.cash.turbine.test
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.common.notDetailsErrorCode
import com.jaimeballesta.domain.common.unknownErrorCode
import com.jaimeballesta.domain.model.detail.CharacterDetailItem
import com.jaimeballesta.marveltest.CoroutinesTestRule
import com.jaimeballesta.marveltest.mocks.itemDetailMock
import com.jaimeballesta.marveltest.presentation.details.state.CharacterDetailsState
import com.jaimeballesta.usecases.details.GetCharacterDetailsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
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
class DetailsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var detailsViewModel: DetailsViewModel

    @Mock
    private lateinit var getCharacterDetailsUseCase: GetCharacterDetailsUseCase

    @Before
    fun setUp(){
        detailsViewModel = DetailsViewModel(getCharacterDetailsUseCase)
    }

    @Test
    fun `getCharacterDetail listen Flows emits a list of characters and change the UI state to LoadDetails`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(listOf(itemDetailMock))

            whenever(getCharacterDetailsUseCase.invoke(itemDetailMock.id)).thenReturn(flowOf(resultExpected))

            detailsViewModel.getCharacterDetail(itemDetailMock.id)

            verify(getCharacterDetailsUseCase).invoke(itemDetailMock.id)

            getCharacterDetailsUseCase(itemDetailMock.id).collect {
                if (it is Resource.Success)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsViewModel.state.test {
                Assert.assertEquals(CharacterDetailsState.LoadDetails(resultExpected.data!!),
                    expectMostRecentItem())
            }
        }
    }

    @Test
    fun `getCharacterDetail listen Flows emits a emptyList and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(emptyList<CharacterDetailItem>())

            whenever(getCharacterDetailsUseCase.invoke(itemDetailMock.id)).thenReturn(flowOf(resultExpected))

            detailsViewModel.getCharacterDetail(itemDetailMock.id)

            verify(getCharacterDetailsUseCase).invoke(itemDetailMock.id)

            getCharacterDetailsUseCase(itemDetailMock.id).collect {
                if (it is Resource.Error)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsViewModel.state.test {
                Assert.assertEquals(CharacterDetailsState.Error(notDetailsErrorCode.toString()),
                    expectMostRecentItem())
            }
        }
    }

    @Test
    fun `getCharacterDetail listen Flows emits a null and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Error<List<CharacterDetailItem>>(unknownErrorCode.toString(), null)

            whenever(getCharacterDetailsUseCase.invoke(itemDetailMock.id)).thenReturn(flowOf(resultExpected))

            detailsViewModel.getCharacterDetail(itemDetailMock.id)

            verify(getCharacterDetailsUseCase).invoke(itemDetailMock.id)

            getCharacterDetailsUseCase(itemDetailMock.id).collect {
                if (it is Resource.Error)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsViewModel.state.test {
                Assert.assertEquals(CharacterDetailsState.Error(unknownErrorCode.toString()),
                    expectMostRecentItem())
            }
        }
    }

}