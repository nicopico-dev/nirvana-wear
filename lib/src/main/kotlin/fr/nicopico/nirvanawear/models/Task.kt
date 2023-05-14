package fr.nicopico.nirvanawear.models

@JvmInline
value class TaskId(val value: String)

data class Task(
    internal val id: TaskId,
    val name: String,
    val completed: Boolean,
)
