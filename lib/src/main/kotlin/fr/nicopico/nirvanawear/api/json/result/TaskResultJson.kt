package fr.nicopico.nirvanawear.api.json.result

import kotlinx.serialization.Serializable

@Suppress("SpellCheckingInspection")
@Serializable
internal data class TaskResultJson(
    val id: String,
    val name: String? = null,
    val note: String? = null,
    val energy: Int? = null,
    val etime: Int? = null,
    val tags: String? = null,
    val waitingfor: String? = null,
    val duedate: String? = null,
    val completed: Int? = null,
    val parentid: String? = null,
    val deleted: Int,
)
