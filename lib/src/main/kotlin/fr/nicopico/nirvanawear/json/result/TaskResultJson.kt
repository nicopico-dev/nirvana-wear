package fr.nicopico.nirvanawear.json.result

import kotlinx.serialization.Serializable

@Serializable
internal data class TaskResultJson(
    val id: String,
    val name: String,
    val completed: Int,
)
