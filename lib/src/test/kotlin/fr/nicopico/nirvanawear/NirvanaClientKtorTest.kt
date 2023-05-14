package fr.nicopico.nirvanawear

import fr.nicopico.nirvanawear.exceptions.AuthenticationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowMessage
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class NirvanaClientKtorTest {

    @Test
    fun `client is able to authenticate`(): Unit = runBlocking {
        val mockEngine = MockEngine { request ->
            request.body.contentType?.withoutParameters()?.toString() shouldBe
                    "application/x-www-form-urlencoded"

            val formData = (request.body as FormDataContent).formData
            formData["method"] shouldBe "auth.new"
            formData["u"] shouldBe "login"
            formData["p"] shouldBe "password"

            respond(
                content = ByteReadChannel(TestResource("fixtures/auth-response.json").content!!),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType, "application/json"
                )
            )
        }
        val client = NirvanaClientKtor(mockEngine)

        val token = client.authenticate("login", "password")

        token.value shouldBe "c3c9e664fdc1d7ab54cb3979f2879a7c2944a98adf3349130b4b4be74b6b34bc"
    }

    @Test
    fun `client should throw an exception if authentication fails`(): Unit = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Invalid username or password",
                status = HttpStatusCode.BadRequest,
            )
        }

        val client = NirvanaClientKtor(mockEngine)

        val error = shouldThrow<AuthenticationException> {
            client.authenticate("login", "password")
        }
        error.reason shouldBe "Invalid username or password"
        error.httpStatus shouldBe 400
    }
}
