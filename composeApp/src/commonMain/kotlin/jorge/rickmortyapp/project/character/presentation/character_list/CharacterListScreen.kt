package jorge.rickmortyapp.project.character.presentation.character_list

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jorge.rickmortyapp.project.character.domain.Character
import jorge.rickmortyapp.project.character.presentation.character_list.components.CharacterList
import jorge.rickmortyapp.project.character.presentation.character_list.components.CharacterSearchBar
import jorge.rickmortyapp.project.core.presentation.DarkBlue
import jorge.rickmortyapp.project.core.presentation.DesertWhite
import jorge.rickmortyapp.project.core.presentation.SandYellow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import rickmortyapp.composeapp.generated.resources.Res
import rickmortyapp.composeapp.generated.resources.no_favorites_result
import rickmortyapp.composeapp.generated.resources.no_search_result


@Composable
fun CharacterListScreenRoot(
    viewModel: CharacterListViewModel = koinViewModel(),
    onCharacterClick: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterListScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is CharacterListAction.onCharacterClick -> onCharacterClick(action.character)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Preview
@Composable
fun CharacterListScreen(
    state: CharacterListState,
    onAction: (CharacterListAction) -> Unit,
    modifier: Modifier = Modifier
){
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState {2}
    val mainListState = rememberLazyListState()
    val searchResultListState = rememberLazyListState()
    val favoriteCharacterListState = rememberLazyListState()


    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
            onAction(CharacterListAction.onTabSelected(pagerState.currentPage))
    }

    LaunchedEffect(mainListState) {
        snapshotFlow { mainListState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                val totalItems = mainListState.layoutInfo.totalItemsCount
                lastVisibleItem != null && lastVisibleItem.index >= totalItems - 5
            }
            .distinctUntilChanged()
            .collect { shouldLoadNext ->
                if (shouldLoadNext && !state.isLoadingNextPage) {
                    onAction(CharacterListAction.LoadNextPage)
                }
            }
    }

    LaunchedEffect(searchResultListState) {
        snapshotFlow { searchResultListState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                val totalItems = searchResultListState.layoutInfo.totalItemsCount
                lastVisibleItem != null && lastVisibleItem.index >= totalItems - 5
            }
            .distinctUntilChanged()
            .collect { shouldLoadNext ->
                if (shouldLoadNext && !state.isLoading) {
                    onAction(CharacterListAction.LoadNextPageSearch)
                }
            }
    }

    println("Total Pages: " + state.totalPages)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(CharacterListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = modifier
                        .padding(vertical = 8.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }

                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(CharacterListAction.onTabSelected(0))
                        },
                        modifier = modifier
                            .weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = "Characters",
                            modifier = modifier
                                .padding(vertical = 12.dp)
                        )
                    }

                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(CharacterListAction.onTabSelected(1))
                        },
                        modifier = modifier
                            .weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = "Favorites",
                            modifier = modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }
                Spacer(
                    modifier = modifier
                        .height(4.dp)
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    Box(
                        modifier = modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when(pageIndex) {
                            0 -> {
                                if(state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        state.searchResults.isEmpty() -> {
                                            CharacterList(
                                                characters = state.allResults,
                                                onCharacterClick = {
                                                    onAction(CharacterListAction.onCharacterClick(it))
                                                },
                                                modifier = modifier.fillMaxSize(),
                                                scrollState = mainListState
                                            )
                                        }
                                        else -> {
                                            CharacterList(
                                                characters = state.searchResults,
                                                onCharacterClick = {
                                                    onAction(CharacterListAction.onCharacterClick(it))
                                                },
                                                modifier = modifier.fillMaxSize(),
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }

                                }

                            }
                            1 -> {
                                if(state.favoriteCharacters.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorites_result),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                } else {
                                    CharacterList(
                                        characters = state.favoriteCharacters,
                                        onCharacterClick = {
                                            onAction(CharacterListAction.onCharacterClick(it))
                                        },
                                        modifier = modifier.fillMaxSize(),
                                        scrollState = favoriteCharacterListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

