package fr.nicopico.nirvanawear.api.json

import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseJson(
    val request: RequestJson,
    val results: List<ResultJson>,
)
