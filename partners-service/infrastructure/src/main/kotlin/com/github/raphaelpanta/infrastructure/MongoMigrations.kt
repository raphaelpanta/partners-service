package com.github.raphaelpanta.infrastructure

import com.github.raphaelpanta.partners.domain.Partner
import com.mongodb.client.MongoClient
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import org.litote.kmongo.getCollection

class MongoMigrations(private val client: MongoClient) {
    fun runMigration() {
        val database = client.getDatabase("partners")

        val partnerCollection = database.getCollection<Partner>()

        partnerCollection.apply {
            createIndex(Indexes.ascending("id"), IndexOptions().unique(true))
            createIndex(Indexes.ascending("document"), IndexOptions().unique(true))
            createIndex(Indexes.geo2dsphere("address"))
        }

    }
}
