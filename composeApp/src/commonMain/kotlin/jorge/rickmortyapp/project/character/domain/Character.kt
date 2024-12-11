package jorge.rickmortyapp.project.character.domain

import jorge.rickmortyapp.project.character.data.dto.CharacterObj
import jorge.rickmortyapp.project.character.data.dto.InfoDto

data class Character(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterObj,
    val location: CharacterObj,
    val image: String,
    val episode: List<String>,
)


data class CharacterObj(
    val name: String,
    val url: String
)

data class PaginatedCharacters(
    val info: InfoDto,
    val characters: List<Character>
)