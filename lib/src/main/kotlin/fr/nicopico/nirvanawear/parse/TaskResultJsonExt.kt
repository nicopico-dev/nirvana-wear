package fr.nicopico.nirvanawear.parse

import fr.nicopico.nirvanawear.json.result.TaskResultJson
import fr.nicopico.nirvanawear.models.Task
import fr.nicopico.nirvanawear.models.TaskId

internal fun TaskResultJson.toTask() : Task = Task(
    id = TaskId(id),
    name = name,
    completed = completed == 1,
)
