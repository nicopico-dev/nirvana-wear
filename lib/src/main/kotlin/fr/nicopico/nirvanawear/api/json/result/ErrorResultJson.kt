package fr.nicopico.nirvanawear.api.json.result

import fr.nicopico.nirvanawear.api.json.RequestJson
import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorResultJson(
    val code: Int,
    val message: String,
    val request: RequestJson,
)
