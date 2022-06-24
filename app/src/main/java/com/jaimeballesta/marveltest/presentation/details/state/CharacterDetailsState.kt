package com.jaimeballesta.marveltest.presentation.details.state

import com.jaimeballesta.domain.model.detail.CharacterDetailItem

sealed class CharacterDetailsState{
    object Loading: CharacterDetailsState()
    data class LoadDetails(val characters: List<CharacterDetailItem>): CharacterDetailsState()
    data class Error(val errorMessage: String): CharacterDetailsState()
}
