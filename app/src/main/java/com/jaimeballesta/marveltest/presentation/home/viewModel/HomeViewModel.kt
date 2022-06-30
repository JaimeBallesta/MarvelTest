package com.jaimeballesta.marveltest.presentation.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaimeballesta.domain.common.unknownErrorCode
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.common.notCharactersErrorCode
import com.jaimeballesta.domain.model.home.CharacterItem
import com.jaimeballesta.marveltest.presentation.home.state.HomeState
import com.jaimeballesta.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    private val _currentCharacters: MutableList<CharacterItem> =
        emptyList<CharacterItem>().toMutableList()
    val currentCharacters = _currentCharacters

    fun getCharacters() = viewModelScope.launch {
        getCharactersUseCase().collect { result ->
            when (result) {
                is Resource.Loading -> setState(HomeState.Loading)
                is Resource.Error -> setErrorState(result.message)
                is Resource.Success -> result.data?.let { characters ->
                    setState(HomeState.LoadCharacters(characters))
                } ?: setErrorState(notCharactersErrorCode.toString())
            }
        }
    }

    fun setCurrentCharacters(filterCharacters: List<CharacterItem>) {
        if (_currentCharacters.isEmpty())
            _currentCharacters.addAll(filterCharacters)
        else {
            _currentCharacters.clear()
            _currentCharacters.addAll(filterCharacters)
        }
    }

    private fun setState(state: HomeState) {
        _state.value = state
    }

    private fun setErrorState(errorMessage: String?) {
        setState(HomeState.Error(errorMessage ?: unknownErrorCode.toString()))
    }
}