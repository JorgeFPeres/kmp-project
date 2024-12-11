package jorge.rickmortyapp.project.character.presentation.character_detail

import jorge.rickmortyapp.project.character.domain.Character

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val character: Character? = null
)
