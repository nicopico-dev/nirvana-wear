package fr.nicopico.nirvanawear.json.result

import kotlinx.serialization.Serializable

@Serializable
internal data class AuthResultJson(
    val token: String,
    val expires: Long,
)
