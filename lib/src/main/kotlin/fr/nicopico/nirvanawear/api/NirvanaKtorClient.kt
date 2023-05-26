package fr.nicopico.nirvanawear.api

import fr.nicopico.nirvanawear.api.exceptions.AuthenticationException
import fr.nicopico.nirvanawear.api.exceptions.FetchTasksException
import fr.nicopico.nirvanawear.api.json.ResponseJson
import fr.nicopico.nirvanawear.models.AuthToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NirvanaKtorClient(
    engine: HttpClientEngine,
    private val appId: String,
    private val baseUrl: String = "https://api.nirvanahq.com"
) {
    private val httpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
            })
        }
    }

    internal suspend fun authenticate(login: String, password: String): ResponseJson {
        val response =  httpClient.submitForm(
            url = "$baseUrl/?api=rest",
            formParameters = Parameters.build {
                append("method", "auth.new")
                append("u", login)
                append("p", password)
            },
        )
        if (response.status.isSuccess()) {
            return response.body<ResponseJson>()
        } else {
            throw AuthenticationException(response.status.value, response.bodyAsText())
        }
    }

    internal suspend fun getResults(token: AuthToken, vararg methods: NirvanaMethods, since: Long = 0): ResponseJson {
        val methodsUrl = methods
            .let {
                if (it.isEmpty()) arrayOf(NirvanaMethods.Everything)
                else methods
            }
            .joinToString(
                separator = "&",
                transform = { "method=${it.value}" },
            )
        val response = httpClient.get(
            urlString = "$baseUrl/?api=rest&appid=$appId&authtoken=${token.value}&$methodsUrl&since=$since"
        )
        if (response.status.isSuccess()) {
            return response.body<ResponseJson>()
        } else {
            val reason = response.bodyAsText()
            throw when (response.status) {
                HttpStatusCode.Forbidden -> AuthenticationException(response.status.value, reason)
                HttpStatusCode.Unauthorized -> AuthenticationException(response.status.value, reason)
                else -> FetchTasksException(response.status.value, reason)
            }
        }
    }
}
