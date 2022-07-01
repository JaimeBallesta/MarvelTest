package com.jaimeballesta.usecases.details

import com.jaimeballesta.data.repository.MarvelRepository
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.model.detail.SectionDetailItem
import com.jaimeballesta.usecases.CoroutinesTestRule
import com.jaimeballesta.usecases.mocks.sectionDetailItemMock
import com.jaimeballesta.usecases.mocks.sectionMock
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
class GetSectionDetailsUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var repository: MarvelRepository

    private lateinit var getSectionDetailsUseCase: GetSectionDetailsUseCase

    @Before
    fun setUp(){
        getSectionDetailsUseCase = GetSectionDetailsUseCase(repository)
    }

    @Test
    fun `invoke must call list sectionDetails from repository`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = listOf(sectionDetailItemMock, sectionDetailItemMock.copy(id = "54321"))
            whenever(repository.getDetailSection(sectionMock, sectionDetailItemMock.id)).thenReturn(resultExpected)

            val result: Flow<Resource<List<SectionDetailItem>>> = getSectionDetailsUseCase(
                sectionMock, sectionDetailItemMock.id
            )

            repository.getDetailSection(sectionMock, sectionDetailItemMock.id)

            verify(repository).getDetailSection(sectionMock, sectionDetailItemMock.id)

            result.collect {
                if (it is Resource.Success)
                    Assert.assertEquals(resultExpected, it.data)
            }
        }
}