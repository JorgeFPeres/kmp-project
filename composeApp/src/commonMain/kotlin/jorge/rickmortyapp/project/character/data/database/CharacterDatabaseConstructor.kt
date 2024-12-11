package jorge.rickmortyapp.project.character.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CharacterDatabaseConstructor: RoomDatabaseConstructor<FavoriteCharacterDatabase> {
    override fun initialize(): FavoriteCharacterDatabase
}