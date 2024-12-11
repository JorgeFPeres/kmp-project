package jorge.rickmortyapp.project.character.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Upsert
    suspend fun upsert(book: CharacterEntity)

    @Query("SELECT * FROM CharacterEntity")
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    suspend fun getFavoriteCharacter(id: String): CharacterEntity?

    @Query("DELETE FROM CharacterEntity WHERE id = :id")
    suspend fun deleteFavoriteCharacter(id: String)
}