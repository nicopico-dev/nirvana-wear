package fr.nicopico.nirvanawear

import fr.nicopico.nirvanawear.api.NirvanaKtorClient
import fr.nicopico.nirvanawear.api.exceptions.AuthenticationException
import fr.nicopico.nirvanawear.api.parsing.tasks
import fr.nicopico.nirvanawear.api.parsing.token
import fr.nicopico.nirvanawear.models.AuthToken
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldMatchEach
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.engine.mock.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

private const val APP_ID = "test"

class NirvanaKtorClientTest {

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
        val client = NirvanaKtorClient(mockEngine, appId = APP_ID)

        val response = client.authenticate("login", "password")

        response.token shouldBe "c3c9e664fdc1d7ab54cb3979f2879a7c2944a98adf3349130b4b4be74b6b34bc"
    }

    @Test
    fun `client should throw an exception if authentication fails`(): Unit = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Invalid username or password",
                status = HttpStatusCode.BadRequest,
            )
        }

        val client = NirvanaKtorClient(mockEngine, appId = APP_ID)

        val error = shouldThrow<AuthenticationException> {
            client.authenticate("login", "password")
        }
        error.reason shouldBe "Invalid username or password"
        error.httpStatus shouldBe 400
    }

    @Test
    fun `client is able to retrieve a list of tasks`(): Unit = runBlocking {
        val authToken = AuthToken("AUTH_TOKEN")
        val mockEngine = MockEngine { request ->
            request.method shouldBe HttpMethod.Get
            request.url.encodedQuery should {
                it shouldContain "authtoken=${authToken.value}"
                it shouldContain "method=tasks"
                it shouldContain "since=0"
            }

            respond(
                content = ByteReadChannel(TestResource("fixtures/get-tasks-response.json").content!!),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType, "application/json"
                )
            )
        }
        val client = NirvanaKtorClient(mockEngine, appId = APP_ID)

        val response = client.fetchTasks(authToken)

        val tasks = response.tasks
        tasks shouldHaveSize 4
        tasks shouldMatchEach listOf(
            {
                it.id shouldBe "B6A227BE-CA94-4ECF-1E3A-56C0EB04CA75"
                it.name shouldBe "Organiser un repas"
            },
            {
                it.id shouldBe "C2D1B278-4486-4F19-5DB9-24AD13527666"
                it.name shouldBe "Acheter un cadeau"
            },
            {
                it.id shouldBe "850608D9-5949-423D-B44A-88F130B62F65"
                it.name shouldBe "targetSDK 33 for all apps"
            },
            {
                it.id shouldBe "9B6E085E-9981-4816-62DB-38DB434ECE22"
                it.name shouldBe "Trouver un nouveau meuble TV"
            },
        )
    }
}
