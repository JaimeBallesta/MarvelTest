package com.jaimeballesta.marveltest.presentation.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.common.notDetailsErrorCode
import com.jaimeballesta.domain.common.unknownErrorCode
import com.jaimeballesta.marveltest.presentation.details.state.CharacterDetailsState
import com.jaimeballesta.usecases.GetCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailsState>(CharacterDetailsState.Loading)
    val state: StateFlow<CharacterDetailsState> = _state

    fun getCharacterDetail(characterId: String) = viewModelScope.launch {
        getCharacterDetailsUseCase(characterId).collect { result ->
            when(result){
                is Resource.Error -> setErrorState(result.message)
                is Resource.Loading -> setState(CharacterDetailsState.Loading)
                is Resource.Success -> result.data?.let { details ->
                    setState(CharacterDetailsState.LoadDetails(details))
                } ?: setErrorState(notDetailsErrorCode.toString())
            }
        }
    }

    private fun setState(state: CharacterDetailsState) {
        _state.value = state
    }

    private fun setErrorState(errorMessage: String?) {
        setState(CharacterDetailsState.Error(errorMessage ?: unknownErrorCode.toString()))
    }

}