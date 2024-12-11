package jorge.rickmortyapp.project.character.presentation.character_list

import jorge.rickmortyapp.project.character.domain.Character
import jorge.rickmortyapp.project.core.presentation.UiText



data class CharacterListState(
    val searchQuery: String = "",
    val searchResults: List<Character> = emptyList(),
    val allResults: List<Character> = emptyList(),
    val favoriteCharacters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val isLoadingNextPage: Boolean = false,
)
