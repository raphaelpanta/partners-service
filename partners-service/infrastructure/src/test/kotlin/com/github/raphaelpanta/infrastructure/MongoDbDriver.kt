package com.github.raphaelpanta.infrastructure

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo

class MongoDbDriver {

    private val mongoExecutable: MongodExecutable = MongodStarter.getDefaultInstance()
            .prepare(MongodConfigBuilder()
                    .version(Version.V4_0_2)
                    .net(Net("localhost", 27017, Network.localhostIsIPv6())).build()
            )

    private val monoProcess: MongodProcess

    val mongoClient: MongoClient

    init {

        monoProcess = mongoExecutable.start()

        mongoClient = KMongo.createClient(MongoClientSettings.builder().apply {
            uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
            applyConnectionString(ConnectionString("mongodb://localhost:27017"))
        }.build())

       MongoMigrations(mongoClient).runMigration()

    }

    fun stop() {
        monoProcess.stop()
        mongoExecutable.stop()
    }
}
