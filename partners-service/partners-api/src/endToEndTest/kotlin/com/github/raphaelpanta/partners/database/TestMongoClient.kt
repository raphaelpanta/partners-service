package com.github.raphaelpanta.partners.database

import com.mongodb.client.MongoClient

class TestMongoClient : AutoCloseable {

    private var closeHandler: AutoCloseable

    val mongoClient: MongoClient

    init {
        val env = System.getenv("ENV")

        mongoClient = if (env == "CONTAINER") EmbeddedMongoClient().apply { closeHandler = this }.mongoClient
        else EmbeddedMongoClient().apply { closeHandler = this }.mongoClient

    }

    override fun close() {
        closeHandler.close()
    }
}
