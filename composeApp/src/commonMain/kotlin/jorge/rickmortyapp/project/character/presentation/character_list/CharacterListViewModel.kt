@file:OptIn(FlowPreview::class)

package jorge.rickmortyapp.project.character.presentation.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jorge.rickmortyapp.project.character.domain.CharacterRepository
import jorge.rickmortyapp.project.core.domain.onError
import jorge.rickmortyapp.project.core.domain.onSuccess
import jorge.rickmortyapp.project.core.presentation.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn


class CharacterListViewModel(
    private val characterRepository: CharacterRepository
): ViewModel() {
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow(CharacterListState())
    val state = _state
        .onStart {
            getAllCharacter()
            observeSearchQuery()
            observeFavoriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )




    fun onAction(action: CharacterListAction) {
        when(action){
            is CharacterListAction.onCharacterClick -> {}
            is CharacterListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is CharacterListAction.onTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
            is CharacterListAction.LoadNextPage -> loadNextPage()
            is CharacterListAction.LoadNextPageSearch -> loadNextPageSearch()
        }
    }

    private fun observeFavoriteBooks() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = characterRepository
            .getFavoriteCharacters()
            .onEach { favoriteChar ->
                _state.update { it.copy(
                    favoriteCharacters = favoriteChar
                ) }
            }
            .launchIn(viewModelScope)
    }


    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when{
                    query.isBlank() -> {
                        getAllCharacter()
                    }
                    query.length >= 2 -> {
                        searchCharacters(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getAllCharacter() = viewModelScope.launch{
        _state.update { it.copy(
            isLoading = true
        ) }

        characterRepository
            .getAllCharacter()
            .onSuccess { results ->
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = null,
                    allResults = results.characters,
                    searchResults = emptyList(),
                    totalPages = results.info.pagesQty,
                    searchQuery = ""
                )}
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText(),
                        searchResults = emptyList(),
                        allResults = emptyList()
                    )
                }
            }
    }

    private fun searchCharacters(query: String) = viewModelScope.launch{
            _state.update { it.copy(
                isLoading = true
            ) }

            characterRepository
                .searchCharacter(query)
                .onSuccess { searchResults ->
                    _state.update { it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults.characters,
                        totalPages = searchResults.info.pagesQty
                    )}
                }
                .onError { error ->
                    _state.update { it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText(),
                        searchResults = emptyList()
                    )}

                }
            }

    private fun loadNextPageSearch() {
        viewModelScope.launch {
            val currentPage = _state.value.currentPage
            val totalPages = _state.value.totalPages


            if (currentPage + 1 > totalPages) return@launch

            _state.update { it.copy(isLoadingNextPage =  true) }

            characterRepository.searchCharacter(_state.value.searchQuery, (currentPage + 1).toString())
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            currentPage = currentPage + 1,
                            searchResults = it.searchResults + response.characters
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun loadNextPage() {
        viewModelScope.launch {
            val currentPage = _state.value.currentPage
            val totalPages = _state.value.totalPages


            if (currentPage + 1 > totalPages) return@launch

            _state.update { it.copy(isLoadingNextPage =  true) }

            characterRepository.getAllCharacter((currentPage + 1).toString())
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            currentPage = currentPage + 1,
                            allResults = it.allResults + response.characters
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
    }

}
