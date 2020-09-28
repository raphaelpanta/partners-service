package com.github.raphaelpanta.partners

import io.javalin.http.contentTypeWrap
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isSameInstanceAs

class PartnerServiceDriver {

    init {
        main()
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = defaultSerializer()
        }
    }

    fun processRequest(request: CreatePartnerRequest) {
        runBlocking {
            val response = client.post<CreatePartnerRequest>("http://localhost:7000/partners/"){
                body = request
                headers.append("Content-Type", "application/json")
            }
            expectThat(response).isEqualTo(request)
        }
    }
}