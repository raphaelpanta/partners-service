package com.github.raphaelpanta.partners.database

import org.litote.kmongo.KMongo
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait

class ContainerizedMongoClient : AutoCloseable {

    private val mongoDBContainer = MongoDBContainer("mongo:4.4.1")
            .apply {
                withExposedPorts(27017, 27017)
            }

    val mongoClient by lazy {
        KMongo.createClient("mongodb://${mongoDBContainer.containerIpAddress}:${mongoDBContainer.firstMappedPort}")
    }

    init {
        mongoDBContainer.waitingFor(Wait.forListeningPort())
                .start()
        System.setProperty("DB_HOST", mongoDBContainer.containerIpAddress)
        System.setProperty("DB_PORT", mongoDBContainer.firstMappedPort.toString())

    }

    override fun close() {
        mongoDBContainer.close()
    }
}