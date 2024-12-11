package jorge.rickmortyapp.project.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object CharacterGraph: Route

    @Serializable
    data object CharacterList: Route

    @Serializable
    data class CharacterDetail(val id: String): Route
}