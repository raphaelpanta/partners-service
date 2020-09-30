package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.PartnerResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.defaultSerializer
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.content.ByteArrayContent
import io.ktor.http.ContentType
import kotlinx.coroutines.runBlocking
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PartnerServiceDriver {

    private val mongoDBContainer = MongoDBContainer("mongo:4.4.1")
            .apply {
                withExposedPorts(27017, 27017)
            }

    init {
        mongoDBContainer.waitingFor(Wait.forListeningPort())
                .start()
        System.setProperty("DB_HOST", mongoDBContainer.containerIpAddress)
        System.setProperty("DB_PORT", mongoDBContainer.firstMappedPort.toString())
        main()
    }

    fun teardown() {
        mongoDBContainer.close()
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = defaultSerializer()
        }
    }

    fun processRequest(request: CreatePartnerRequest) {
        runBlocking {
            val response = client.post<PartnerResponse>("http://localhost:7000/partners/") {
                body = request
                headers.append("Content-Type", "application/json")
            }
            expectThat(response.copy(id = "")).isEqualTo(createPartnerResponse)
        }
    }

    fun insertPartner(request: String) = runBlocking {
        val response = client.post<PartnerResponse>("http://localhost:7000/partners/") {
            body = ByteArrayContent(request.toByteArray(), ContentType.Application.Json)
        }
        response.id
    }

    fun queryPartner(id: String) = runBlocking {
        client.get<PartnerResponse>("http://localhost:7000/partners/$id")
    }

}