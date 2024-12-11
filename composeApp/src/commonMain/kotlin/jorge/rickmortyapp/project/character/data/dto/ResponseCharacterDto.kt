package jorge.rickmortyapp.project.character.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCharacterDto(
    @SerialName("info") val info: InfoDto,
    @SerialName("results") val results: List<SearchCharacterDto>
)

@Serializable
data class InfoDto(
    @SerialName("pages") val pagesQty: Int,
)


