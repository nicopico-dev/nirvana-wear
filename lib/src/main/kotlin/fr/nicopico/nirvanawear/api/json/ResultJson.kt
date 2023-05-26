package fr.nicopico.nirvanawear.api.json

import fr.nicopico.nirvanawear.api.json.result.AuthResultJson
import fr.nicopico.nirvanawear.api.json.result.ErrorResultJson
import fr.nicopico.nirvanawear.api.json.result.PrefResultJson
import fr.nicopico.nirvanawear.api.json.result.TagResultJson
import fr.nicopico.nirvanawear.api.json.result.TaskResultJson
import kotlinx.serialization.Serializable

@Serializable
internal data class ResultJson(
    val auth: AuthResultJson? = null,
    val pref: PrefResultJson? = null,
    val tag: TagResultJson? = null,
    val task: TaskResultJson? = null,
    val error: ErrorResultJson? = null,
    val userResultJson: ErrorResultJson? = null,
)
