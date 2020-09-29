package com.github.raphaelpanta.infrastructure

import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import com.mongodb.client.MongoClient
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class MongoDbPartnersRepository(mongoClient: MongoClient) : PartnersRepository {
    val collection = mongoClient.getDatabase("partners")
            .getCollection<Partner>()

    override fun create(partner: Partner): Partner? {
        collection.insertOne(partner)
        return collection.findOne("{ id : \"${partner.id}\"  }")
    }
}