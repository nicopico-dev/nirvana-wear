package fr.nicopico.nirvanawear.json

import fr.nicopico.nirvanawear.json.result.ResultJson
import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseJson(
    val results: List<ResultJson>
)
