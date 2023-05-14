package fr.nicopico.nirvanawear.json.result

import kotlinx.serialization.Serializable

@Serializable
internal data class ResultJson(
    val auth: AuthResultJson? = null,
    val task: TaskResultJson? = null
)
