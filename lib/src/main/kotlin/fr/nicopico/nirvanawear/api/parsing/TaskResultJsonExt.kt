package fr.nicopico.nirvanawear.api.parsing

import fr.nicopico.nirvanawear.api.json.result.TaskResultJson
import fr.nicopico.nirvanawear.models.Task
import fr.nicopico.nirvanawear.models.TaskId

internal fun TaskResultJson.toTask() : Task = Task(
    id = TaskId(id),
    name = name,
    completed = completed == 1,
)
