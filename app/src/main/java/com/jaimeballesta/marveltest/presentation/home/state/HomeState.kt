package com.jaimeballesta.marveltest.presentation.home.state

import com.jaimeballesta.domain.model.CharacterItem

sealed class HomeState {
    object Loading: HomeState()
    data class LoadCharacters(val characters: List<CharacterItem>): HomeState()
    data class Error(val errorMessage: String): HomeState()
}