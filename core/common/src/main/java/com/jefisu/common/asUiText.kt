package com.jefisu.common

import com.jefisu.data_remote.ApiError
import com.jefisu.domain.util.Error

fun Error.asUiText(): UiText {
    return when (this) {
        ApiError.NO_INTERNET -> UiText.StringResource(R.string.no_internet_error)
        ApiError.AUTHENTICATION_FAILED -> UiText.StringResource(R.string.server_error)
        ApiError.SERVER_ERROR -> UiText.StringResource(R.string.server_error)
        else -> UiText.StringResource(R.string.unknown_error)
    }
}