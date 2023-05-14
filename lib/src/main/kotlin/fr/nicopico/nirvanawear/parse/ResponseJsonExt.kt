package fr.nicopico.nirvanawear.parse

import fr.nicopico.nirvanawear.exceptions.ParsingException
import fr.nicopico.nirvanawear.json.ResponseJson
import fr.nicopico.nirvanawear.json.result.TaskResultJson

internal val ResponseJson.token: String
    get() = results[0]
        .auth
        ?.token
        ?: throw ParsingException("Unable to retrieve auth token from response")

internal val ResponseJson.tasks: List<TaskResultJson>
    get() = results
        .mapNotNull { it.task }
