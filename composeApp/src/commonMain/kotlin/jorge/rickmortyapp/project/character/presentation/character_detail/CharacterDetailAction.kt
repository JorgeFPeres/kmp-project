package jorge.rickmortyapp.project.character.presentation.character_detail

import jorge.rickmortyapp.project.character.domain.Character

sealed interface CharacterDetailAction {
    data object OnBackClick: CharacterDetailAction
    data object OnFavoriteClick: CharacterDetailAction
    data class OnSelectedCharacterChange(val character: Character): CharacterDetailAction
}