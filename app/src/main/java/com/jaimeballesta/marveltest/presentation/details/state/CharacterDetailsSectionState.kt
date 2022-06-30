package com.jaimeballesta.marveltest.presentation.details.state

import com.jaimeballesta.domain.model.detail.SectionDetailItem

sealed class CharacterDetailsSectionState{
    object Loading: CharacterDetailsSectionState()
    data class LoadSectionDetails(val details: List<SectionDetailItem>): CharacterDetailsSectionState()
    data class Error(val errorMessage: String): CharacterDetailsSectionState()
}
