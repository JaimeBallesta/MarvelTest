package com.jaimeballesta.marveltest.presentation.details.viewmodel

import app.cash.turbine.test
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.common.notDetailsErrorCode
import com.jaimeballesta.domain.common.unknownErrorCode
import com.jaimeballesta.domain.model.detail.SectionDetailItem
import com.jaimeballesta.marveltest.CoroutinesTestRule
import com.jaimeballesta.marveltest.mocks.sectionDetailItemMock
import com.jaimeballesta.marveltest.mocks.sectionMock
import com.jaimeballesta.marveltest.presentation.details.state.CharacterDetailsSectionState
import com.jaimeballesta.usecases.details.GetSectionDetailsUseCase
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
class DetailsSectionViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var detailsSectionViewModel: DetailsSectionViewModel

    @Mock
    private lateinit var getSectionDetailsUseCase: GetSectionDetailsUseCase

    @Before
    fun setUp(){
        detailsSectionViewModel = DetailsSectionViewModel(getSectionDetailsUseCase)
    }

    @Test
    fun `getCharacterSectionDetail listen Flows emits a list of characters and change the UI state to LoadSectionDetails`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(listOf(sectionDetailItemMock, sectionDetailItemMock.copy(id = "54321")))

            whenever(getSectionDetailsUseCase.invoke(sectionMock, sectionDetailItemMock.id)).thenReturn(flowOf(resultExpected))

            detailsSectionViewModel.getCharacterSectionDetail(sectionMock, sectionDetailItemMock.id)

            verify(getSectionDetailsUseCase).invoke(sectionMock, sectionDetailItemMock.id)

            getSectionDetailsUseCase(sectionMock, sectionDetailItemMock.id).collect {
                if (it is Resource.Success)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsSectionViewModel.state.test {
                Assert.assertEquals(CharacterDetailsSectionState.LoadSectionDetails(resultExpected.data!!),
                    expectMostRecentItem())
            }
        }
    }

    @Test
    fun `getCharacterSectionDetail listen Flows emits a emptyList and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Success(emptyList<SectionDetailItem>())

            whenever(getSectionDetailsUseCase.invoke(sectionMock, sectionDetailItemMock.id)).thenReturn(flowOf(resultExpected))

            detailsSectionViewModel.getCharacterSectionDetail(sectionMock, sectionDetailItemMock.id)

            verify(getSectionDetailsUseCase).invoke(sectionMock, sectionDetailItemMock.id)

            getSectionDetailsUseCase(sectionMock, sectionDetailItemMock.id).collect {
                if (it is Resource.Error)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsSectionViewModel.state.test {
                Assert.assertEquals(CharacterDetailsSectionState.Error(notDetailsErrorCode.toString()),
                    expectMostRecentItem())
            }
        }
    }

    @Test
    fun `getCharacterDetail listen Flows emits a null and change the UI state to Error`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = Resource.Error<List<SectionDetailItem>>(unknownErrorCode.toString(), null)

            whenever(getSectionDetailsUseCase.invoke(sectionMock, sectionDetailItemMock.id)).thenReturn(flowOf(resultExpected))

            detailsSectionViewModel.getCharacterSectionDetail(sectionMock, sectionDetailItemMock.id)

            verify(getSectionDetailsUseCase).invoke(sectionMock, sectionDetailItemMock.id)

            getSectionDetailsUseCase(sectionMock, sectionDetailItemMock.id).collect {
                if (it is Resource.Error)
                    Assert.assertEquals(resultExpected.data, it.data)
            }

            detailsSectionViewModel.state.test {
                Assert.assertEquals(CharacterDetailsSectionState.Error(unknownErrorCode.toString()),
                    expectMostRecentItem())
            }
        }
    }


}