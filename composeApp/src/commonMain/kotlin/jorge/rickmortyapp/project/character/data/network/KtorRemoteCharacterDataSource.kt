package jorge.rickmortyapp.project.character.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import jorge.rickmortyapp.project.character.data.dto.ResponseCharacterDto
import jorge.rickmortyapp.project.core.data.safeCall
import jorge.rickmortyapp.project.core.domain.DataError
import jorge.rickmortyapp.project.core.domain.Result

private const val BASE_URL = "https://rickandmortyapi.com/api"

class KtorRemoteCharacterDataSource(
    private val httpClient: HttpClient
): RemoteCharacterDataSource {

    override suspend fun getAllCharacters(page: String?): Result<ResponseCharacterDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/character/"
            ) {
                if(!page.isNullOrEmpty()){
                    parameter("page", page)
                }
            }
        }
    }

    override suspend fun searchCharacters(
        query: String,
        page: String?
    ): Result<ResponseCharacterDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/character/"
            ) {
                parameter("name", query)
                if (!page.isNullOrEmpty()) {
                    parameter("page", page)
                }
            }
        }
    }

}