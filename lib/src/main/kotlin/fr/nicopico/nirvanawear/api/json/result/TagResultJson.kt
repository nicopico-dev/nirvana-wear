package fr.nicopico.nirvanawear.api.json.result

import kotlinx.serialization.Serializable

@Serializable
internal data class TagResultJson(
    val key: String,
    val type: String? = null,
    val color: String? = null,
    val deleted: Int,
)
