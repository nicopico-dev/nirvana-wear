package fr.nicopico.nirvanawear.exceptions

class FetchTasksException(
    val httpStatus: Int,
    val reason: String
) : Exception()
