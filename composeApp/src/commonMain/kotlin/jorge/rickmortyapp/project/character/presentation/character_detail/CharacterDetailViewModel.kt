package jorge.rickmortyapp.project.character.presentation.character_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import jorge.rickmortyapp.project.app.Route
import jorge.rickmortyapp.project.character.domain.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CharacterDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val characterId = savedStateHandle.toRoute<Route.CharacterDetail>().id
    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state
        .onStart {
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    fun onAction(action: CharacterDetailAction) {
        when (action) {
            is CharacterDetailAction.OnSelectedCharacterChange -> {
                _state.update {
                    it.copy(
                        character = action.character
                    )
                }
            }

            is CharacterDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        characterRepository.deleteFromFavorites(characterId)
                    } else {
                        state.value.character?.let { book ->
                            characterRepository.markAsFavorite(book)
                        }
                    }
                }
            }

            else -> Unit
        }
    }

    private fun observeFavoriteStatus() {
        characterRepository
            .isCharacterFavorite(characterId)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        isFavorite = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}