package jorge.rickmortyapp.project.character.data.repository


import androidx.sqlite.SQLiteException
import jorge.rickmortyapp.project.character.data.database.FavoriteCharacterDao
import jorge.rickmortyapp.project.character.data.mappers.toCharacter
import jorge.rickmortyapp.project.character.data.mappers.toCharacterEntity
import jorge.rickmortyapp.project.character.data.network.RemoteCharacterDataSource
import jorge.rickmortyapp.project.character.domain.Character
import jorge.rickmortyapp.project.character.domain.CharacterRepository
import jorge.rickmortyapp.project.character.domain.PaginatedCharacters
import jorge.rickmortyapp.project.core.domain.DataError
import jorge.rickmortyapp.project.core.domain.EmptyResult
import jorge.rickmortyapp.project.core.domain.Result
import jorge.rickmortyapp.project.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DefaultRepository(
    private val remoteCharacterDataSource: RemoteCharacterDataSource,
    private val favoriteCharacteDao: FavoriteCharacterDao
) : CharacterRepository {
    override suspend fun getAllCharacter(page: String?): Result<PaginatedCharacters, DataError.Remote> {
        return remoteCharacterDataSource
            .getAllCharacters(page)
            .map { dto ->
                PaginatedCharacters(
                    dto.info,
                    dto.results.map { it.toCharacter() }
                )
            }
    }

    override suspend fun searchCharacter(
        query: String,
        page: String?
    ): Result<PaginatedCharacters, DataError.Remote> {
        return remoteCharacterDataSource
            .searchCharacters(query, page)
            .map { dto ->
                PaginatedCharacters(
                    dto.info,
                    dto.results.map { it.toCharacter() }
                )
            }
    }

    override fun getFavoriteCharacters(): Flow<List<Character>> {
        return favoriteCharacteDao
            .getFavoriteCharacters()
            .map { characterEntities ->
                characterEntities.map { it.toCharacter() }
            }
    }

    override fun isCharacterFavorite(id: String): Flow<Boolean> {
        return favoriteCharacteDao
            .getFavoriteCharacters()
            .map { characterEntities ->
                characterEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(character: Character): EmptyResult<DataError.Local> {
        return try {
            favoriteCharacteDao.upsert(character.toCharacterEntity())
            Result.Success(Unit)
        } catch(e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteCharacteDao.deleteFavoriteCharacter(id)
    }


}