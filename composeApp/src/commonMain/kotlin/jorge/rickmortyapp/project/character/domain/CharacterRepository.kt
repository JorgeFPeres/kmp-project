package jorge.rickmortyapp.project.character.domain

import jorge.rickmortyapp.project.core.domain.DataError
import jorge.rickmortyapp.project.core.domain.EmptyResult
import jorge.rickmortyapp.project.core.domain.Result
import kotlinx.coroutines.flow.Flow


interface CharacterRepository {
    suspend fun getAllCharacter(page: String? = null): Result<PaginatedCharacters, DataError.Remote>
    suspend fun searchCharacter(
        query: String,
        page: String? = null
    ): Result<PaginatedCharacters, DataError.Remote>


    fun getFavoriteCharacters(): Flow<List<Character>>
    fun isCharacterFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(character: Character): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}