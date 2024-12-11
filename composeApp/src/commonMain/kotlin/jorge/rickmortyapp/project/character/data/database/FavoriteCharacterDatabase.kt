package jorge.rickmortyapp.project.character.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(CharacterDatabaseConstructor::class)
abstract class FavoriteCharacterDatabase: RoomDatabase() {
    abstract val favoriteBookDao: FavoriteCharacterDao

    companion object {
        const val DB_NAME = "book.db"
    }
}