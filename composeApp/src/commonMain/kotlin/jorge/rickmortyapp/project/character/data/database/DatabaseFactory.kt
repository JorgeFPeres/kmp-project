package jorge.rickmortyapp.project.character.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavoriteCharacterDatabase>
}