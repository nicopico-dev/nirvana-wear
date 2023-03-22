package fr.nicopico.nirvanawear.models.json

import fr.nicopico.nirvanawear.models.AuthToken
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthResponse(
    val results: List<ResultJson>
) {
    val token: AuthToken
        get() = results[0].auth.token.let { AuthToken(it) }
}

@Serializable
internal data class ResultJson(
    val auth: AuthResultJson
)

@Serializable
internal data class AuthResultJson(
    val token: String,
    val expires: Long,
)
