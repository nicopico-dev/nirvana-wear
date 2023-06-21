package fr.nicopico.nirvanawear.api.exceptions

class FetchTasksException(
    val httpStatus: Int,
    val reason: String
) : Exception()
