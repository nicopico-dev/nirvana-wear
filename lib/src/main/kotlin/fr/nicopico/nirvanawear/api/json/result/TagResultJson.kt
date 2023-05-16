package fr.nicopico.nirvanawear.api.json.result

import kotlinx.serialization.Serializable

@Serializable
internal data class TagResultJson(
    val key: String,
    val type: String,
    val color: String,
    val deleted: Int,
)
