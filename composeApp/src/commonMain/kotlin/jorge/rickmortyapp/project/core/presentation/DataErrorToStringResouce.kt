package jorge.rickmortyapp.project.core.presentation

import jorge.rickmortyapp.project.core.domain.DataError
import rickmortyapp.composeapp.generated.resources.Res
import rickmortyapp.composeapp.generated.resources.error_disk_full
import rickmortyapp.composeapp.generated.resources.error_no_internet
import rickmortyapp.composeapp.generated.resources.error_request_timeout
import rickmortyapp.composeapp.generated.resources.error_serialization
import rickmortyapp.composeapp.generated.resources.error_too_many_request
import rickmortyapp.composeapp.generated.resources.error_unknown

fun DataError.toUiText(): UiText {
    val stringRes = when(this){
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUEST -> Res.string.error_too_many_request
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER -> Res.string.error_unknown
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }

    return UiText.StringResourceId(stringRes)
}