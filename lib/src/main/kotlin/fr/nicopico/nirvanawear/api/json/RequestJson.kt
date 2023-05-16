package fr.nicopico.nirvanawear.api.json

import kotlinx.serialization.Serializable

@Serializable
internal data class RequestJson(
    val api: String,
    //val since: Long,
    val method: String,
)
