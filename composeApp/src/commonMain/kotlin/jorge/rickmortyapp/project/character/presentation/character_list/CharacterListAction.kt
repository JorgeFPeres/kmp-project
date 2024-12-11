package jorge.rickmortyapp.project.character.presentation.character_list

import jorge.rickmortyapp.project.character.domain.Character

sealed interface CharacterListAction {
    data class OnSearchQueryChange(val query: String) : CharacterListAction
    data class onCharacterClick(val character: Character) : CharacterListAction
    data class onTabSelected(val index: Int) : CharacterListAction
    object LoadNextPage : CharacterListAction
    object LoadNextPageSearch : CharacterListAction
}
