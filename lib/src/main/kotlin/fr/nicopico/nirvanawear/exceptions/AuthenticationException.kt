package fr.nicopico.nirvanawear.exceptions

class AuthenticationException(
    val httpStatus: Int,
    val reason: String
) : Exception()
