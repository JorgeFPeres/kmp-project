package jorge.rickmortyapp.project.character.presentation.character_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jorge.rickmortyapp.project.character.domain.Character

@Composable
fun CharacterList(
    characters: List<Character>,
    onCharacterClick: (Character) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = characters,
            key = {
                it.id
            }
            ) { character ->
            CharacterListItem(
                character = character,
                modifier = Modifier
                    .widthIn(700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onCharacterClick(character)
                }
            )
        }
    }
}