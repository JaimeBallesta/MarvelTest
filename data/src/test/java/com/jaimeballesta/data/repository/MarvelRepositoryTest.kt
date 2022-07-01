package com.jaimeballesta.data.repository

import com.jaimeballesta.data.CoroutinesTestRule
import com.jaimeballesta.data.mocks.characterItemMock
import com.jaimeballesta.data.mocks.itemDetailMock
import com.jaimeballesta.data.mocks.sectionMock
import com.jaimeballesta.data.mocks.sectionDetailItemMock
import com.jaimeballesta.data.source.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MarvelRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Test
    fun `getCharacters must call to remote characters `() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = listOf(characterItemMock, characterItemMock.copy(id = "54321"))
            whenever(remoteDataSource.getCharacters()).thenReturn(resultExpected)

            remoteDataSource.getCharacters()

            verify(remoteDataSource).getCharacters()
        }
    }

    @Test
    fun `getDetailsCharacter must call to remote characterDetails `() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = listOf(itemDetailMock)
            whenever(remoteDataSource.getDetailsCharacter(itemDetailMock.id)).thenReturn(resultExpected)

            remoteDataSource.getDetailsCharacter(itemDetailMock.id)

            verify(remoteDataSource).getDetailsCharacter(itemDetailMock.id)
        }
    }

    @Test
    fun `getDetailSection must call to remote characterSectionDetail `() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val resultExpected = listOf(sectionDetailItemMock)
            whenever(remoteDataSource.getDetailSection(sectionMock,  itemDetailMock.id)).thenReturn(resultExpected)

            remoteDataSource.getDetailSection(sectionMock, itemDetailMock.id)

            verify(remoteDataSource).getDetailSection(sectionMock, itemDetailMock.id)
        }
    }
}