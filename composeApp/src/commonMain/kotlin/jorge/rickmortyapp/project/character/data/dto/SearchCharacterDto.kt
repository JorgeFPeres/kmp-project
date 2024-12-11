package jorge.rickmortyapp.project.character.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchCharacterDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String,
    @SerialName("type") val type: String,
    @SerialName("gender") val gender: String,
    @SerialName("origin") val origin: CharacterObj,
    @SerialName("location") val location: CharacterObj,
    @SerialName("image") val image: String,
    @SerialName("episode") val episode: List<String>,
)

@Serializable
data class CharacterObj(
    @SerialName("name") val name: String,
    @SerialName("url")val url: String
)

