package com.github.raphaelpanta.partners

import com.github.raphaelpanta.infrastructure.MongoDbPartnersRepository
import com.github.raphaelpanta.partners.service.PartnerKonformValidator
import com.github.raphaelpanta.partners.service.PartnersAppService
import io.javalin.Javalin
import org.litote.kmongo.KMongo

fun main() {
    val app = Javalin.create().start(7000)
    val host = System.getProperty("DB_HOST")
    val port = System.getProperty("DB_PORT")
    val mongoClient = KMongo.createClient("mongodb://$host:$port")
    val partnersRepository = MongoDbPartnersRepository(mongoClient)
    val createPartnerValidator = PartnerKonformValidator()
    val partnerService = PartnersAppService(partnersRepository, createPartnerValidator)
    val partnerController = PartnerController(partnerService)
    val partnersRoutes = PartnersRoutes(partnerController)

    app.routes(partnersRoutes::routes)

}