package com.github.raphaelpanta.infrastructure

import com.github.michaelbull.result.runCatching
import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import com.mongodb.client.MongoClient
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import java.util.UUID

class MongoDbPartnersRepository(mongoClient: MongoClient) : PartnersRepository {
    private val collection = mongoClient.getDatabase("partners")
            .getCollection<Partner>()

    override fun create(partner: Partner) = runCatching {
        collection.insertOne(partner).run {
            collection.findOne("{ id : \"${partner.id}\"  }")
                    ?: throw IllegalStateException("An error has occurred")
        }
    }

    override fun find(id: UUID) = runCatching {
        collection.findOne("{ id : \"${id}\"  }")
    }
}
