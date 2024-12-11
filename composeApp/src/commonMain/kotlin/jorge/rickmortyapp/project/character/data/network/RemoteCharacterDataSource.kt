package jorge.rickmortyapp.project.character.data.network

import jorge.rickmortyapp.project.character.data.dto.ResponseCharacterDto
import jorge.rickmortyapp.project.core.domain.DataError
import jorge.rickmortyapp.project.core.domain.Result

interface RemoteCharacterDataSource {
    suspend fun getAllCharacters(page: String? = null) : Result<ResponseCharacterDto, DataError.Remote>

    suspend fun searchCharacters(
        query: String,
        page: String? = null
    ): Result<ResponseCharacterDto, DataError.Remote>
}