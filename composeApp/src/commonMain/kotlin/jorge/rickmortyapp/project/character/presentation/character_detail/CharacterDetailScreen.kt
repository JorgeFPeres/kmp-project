package jorge.rickmortyapp.project.character.presentation.character_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jorge.rickmortyapp.project.character.presentation.character_detail.components.BlurredImageBackGround
import jorge.rickmortyapp.project.character.presentation.character_detail.components.CharacterChip
import jorge.rickmortyapp.project.character.presentation.character_detail.components.TitledContent
import org.jetbrains.compose.resources.stringResource
import rickmortyapp.composeapp.generated.resources.Res
import rickmortyapp.composeapp.generated.resources.gender
import rickmortyapp.composeapp.generated.resources.origin
import rickmortyapp.composeapp.generated.resources.species
import rickmortyapp.composeapp.generated.resources.status


@Composable
fun CharacterDetailScreenRoot(
    viewModel: CharacterDetailViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterDetailScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is CharacterDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
private fun CharacterDetailScreen(
    state: CharacterDetailState,
    onAction: (CharacterDetailAction) -> Unit
) {
    BlurredImageBackGround(
        imageUrl = state.character?.image,
        isFavorite = state.isFavorite,
        onFavoriteClick =  {
            onAction(CharacterDetailAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(CharacterDetailAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if(state.character != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 32.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = state.character.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TitledContent(
                        title = stringResource(Res.string.status),
                    ) {
                        CharacterChip {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = state.character.status
                                )
                                Icon(
                                    imageVector = if (state.character.status == "Alive") Icons.Default.Done else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (state.character.status == "Alive") Color.Green else Color.Red,
                                )
                            }
                        }
                    }
                    TitledContent(
                        title = stringResource(Res.string.species),
                    ) {
                        CharacterChip {
                            Text(
                                text = state.character.species
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TitledContent(
                        title = stringResource(Res.string.gender),
                    ) {
                        CharacterChip {
                            Text(
                                text = state.character.gender
                            )
                        }
                    }
                    TitledContent(
                        title = stringResource(Res.string.origin),
                    ) {
                        CharacterChip {
                            Text(
                                text = state.character.origin.name
                            )
                        }
                    }
                }
            }
        }
    }
}