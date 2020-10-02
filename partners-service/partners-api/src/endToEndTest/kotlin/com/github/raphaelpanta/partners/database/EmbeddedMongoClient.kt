package com.github.raphaelpanta.partners.database

import com.mongodb.client.MongoClient
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.litote.kmongo.KMongo

class EmbeddedMongoClient : AutoCloseable {

    private val mongoExecutable: MongodExecutable = MongodStarter.getDefaultInstance()
            .prepare(MongodConfigBuilder()
                    .version(Version.V4_0_2)
                    .net(Net("localhost", 27017, Network.localhostIsIPv6())).build()
            )

    private val monoProcess: MongodProcess

    val mongoClient: MongoClient

    init {

        System.setProperty("DB_HOST", "localhost")
        System.setProperty("DB_PORT", "27017")

        monoProcess = mongoExecutable.start()

        mongoClient = KMongo.createClient("mongodb://localhost:27017")

    }

    override fun close() {
        monoProcess.stop()
        mongoExecutable.stop()
    }

}