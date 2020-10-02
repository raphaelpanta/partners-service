package com.github.raphaelpanta.partners

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.raphaelpanta.infrastructure.MongoDbPartnersRepository
import com.github.raphaelpanta.infrastructure.MongoMigrations
import com.github.raphaelpanta.partners.Main.serverPort
import com.github.raphaelpanta.partners.service.PartnerKonformValidator
import com.github.raphaelpanta.partners.service.PartnersAppService
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.litote.kmongo.KMongo

fun main() {

    val host = System.getenv("DB_HOST") ?: System.getProperty("DB_HOST")
    val port = System.getenv("DB_PORT") ?: System.getProperty("DB_PORT")
    val mongoClient = KMongo.createClient("mongodb://$host:$port")
    MongoMigrations(mongoClient).runMigration()
    val partnersRepository = MongoDbPartnersRepository(mongoClient)
    val createPartnerValidator = PartnerKonformValidator()
    val partnerService = PartnersAppService(partnersRepository, createPartnerValidator)
    val partnerController = PartnerController(partnerService)
    val partnersRoutes = PartnersRoutes(partnerController)

    JavalinJackson.configure(jacksonObjectMapper()
            .registerModule(SimpleModule())
            .configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, true)
            .configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, true)
            .registerKotlinModule())
    Javalin.create()
            .routes(partnersRoutes::routes)
            .start(serverPort)

}

object Main {
    const val serverPort = 7000
}
