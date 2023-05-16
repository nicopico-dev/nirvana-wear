package fr.nicopico.nirvanawear.api.parsing

import fr.nicopico.nirvanawear.api.exceptions.ParsingException
import fr.nicopico.nirvanawear.api.json.ResponseJson
import fr.nicopico.nirvanawear.api.json.result.TaskResultJson

internal val ResponseJson.token: String
    get() = results[0]
        .auth
        ?.token
        ?: throw ParsingException("Unable to retrieve auth token from response")

internal val ResponseJson.tasks: List<TaskResultJson>
    get() = results
        .mapNotNull { it.task }
