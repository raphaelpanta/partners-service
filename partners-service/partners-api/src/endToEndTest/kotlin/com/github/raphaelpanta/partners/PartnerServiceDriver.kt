package com.github.raphaelpanta.partners

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.raphaelpanta.infrastructure.MongoMigrations
import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.PartnerResponse
import com.github.raphaelpanta.partners.service.toPartner
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.defaultSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.content.ByteArrayContent
import io.ktor.http.ContentType
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class PartnerServiceDriver {

    private val mongoDBContainer = MongoDBContainer("mongo:4.4.1")
            .apply {
                withExposedPorts(27017, 27017)
            }

    private val mongoClient by lazy {
        KMongo.createClient("mongodb://${mongoDBContainer.containerIpAddress}:${mongoDBContainer.firstMappedPort}")
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
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    fun processRequest(request: CreatePartnerRequest) = runBlocking {
        client.post<PartnerResponse>("http://localhost:7000/partners/") {
            body = request
            headers.append("Content-Type", "application/json")
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

    fun queryPartner(coordinates: Pair<Double, Double>) = runBlocking {
        coordinates
                .let {
                    (URLEncoder.encode(it.first.toString(), StandardCharsets.UTF_8) to
                            URLEncoder.encode(it.second.toString(), StandardCharsets.UTF_8))
                }
                .let { (long, lat) ->
                    client.get<PartnerResponse>("http://localhost:7000/partners/$long/$lat/")
                }
    }

    fun fillDatabase() {

        val coll = mongoClient.getDatabase("partners").getCollection<Partner>()

        coll.drop()

        MongoMigrations(mongoClient).runMigration()

        this::class
                .java
                .classLoader
                .getResourceAsStream("com/github/raphaelpanta/partners/partners.json")
                ?.use {
                    ObjectMapper().registerKotlinModule()
                            .readValue<List<CreatePartnerRequest>>(it)
                }
                ?.map { it.toPartner() }
                ?.let {
                    coll.insertMany(it)
                }
    }
}
