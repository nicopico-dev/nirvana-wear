package fr.nicopico.nirvanawear.api.json.result

import kotlinx.serialization.Serializable

@Serializable
internal data class PrefResultJson(
    val key: String,
    val value: String,
    val deleted: Int,
)
