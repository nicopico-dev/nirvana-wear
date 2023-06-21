package fr.nicopico.nirvanawear.api.exceptions

class AuthenticationException(
    val httpStatus: Int,
    val reason: String
) : Exception()
