package com.jaimeballesta.marveltest.presentation.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaimeballesta.domain.common.Resource
import com.jaimeballesta.domain.common.notDetailsErrorCode
import com.jaimeballesta.domain.common.unknownErrorCode
import com.jaimeballesta.marveltest.presentation.details.state.CharacterDetailsSectionState
import com.jaimeballesta.usecases.GetSectionDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsSectionViewModel @Inject constructor(
    private val getSectionDetailsUseCase: GetSectionDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailsSectionState>(CharacterDetailsSectionState.Loading)
    val state: StateFlow<CharacterDetailsSectionState> = _state


    fun getCharacterSectionDetail(section: String, characterId: String) = viewModelScope.launch {
        getSectionDetailsUseCase(section, characterId).collect { result ->
            when (result) {
                is Resource.Error -> setErrorState(result.message)
                is Resource.Loading -> setState(CharacterDetailsSectionState.Loading)
                is Resource.Success -> result.data?.let { details ->
                    setState(CharacterDetailsSectionState.LoadSectionDetails(details))
                } ?: setErrorState(notDetailsErrorCode.toString())
            }
        }
    }

    private fun setState(state: CharacterDetailsSectionState) {
        _state.value = state
    }

    private fun setErrorState(errorMessage: String?) {
        setState(CharacterDetailsSectionState.Error(errorMessage ?: unknownErrorCode.toString()))
    }

}