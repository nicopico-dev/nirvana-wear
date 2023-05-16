package fr.nicopico.nirvanawear.api.json.result

import kotlinx.serialization.Serializable

@Serializable
internal data class AuthResultJson(
    val token: String,
    val expires: Long,
)
