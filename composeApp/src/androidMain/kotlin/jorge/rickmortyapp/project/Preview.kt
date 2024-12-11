package jorge.rickmortyapp.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import jorge.rickmortyapp.project.character.domain.Character
import jorge.rickmortyapp.project.character.domain.CharacterObj
import jorge.rickmortyapp.project.character.presentation.character_list.CharacterListScreen
import jorge.rickmortyapp.project.character.presentation.character_list.CharacterListState
import jorge.rickmortyapp.project.character.presentation.character_list.components.CharacterSearchBar

@Preview(backgroundColor = 0xFFF)
@Composable
private fun CharacterSearchBarPreview() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ){

        CharacterSearchBar(
            searchQuery = "rick",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier.fillMaxWidth()
        )

        }
}


@Preview(backgroundColor = 0xFFF)
@Composable
private fun CharacterListPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ){

        CharacterListScreen(
            state = CharacterListState(
                searchResults = emptyList()
            ),
            onAction = { }
        )

    }
}
