package fr.nicopico.nirvanawear.api.json.result

import kotlinx.serialization.Serializable

@Serializable
@Suppress("SpellCheckingInspection")
internal data class UserResultJson(
    val id: String,
    val emailaddress: String,
    val lastwritebyuseron: Long,
)
