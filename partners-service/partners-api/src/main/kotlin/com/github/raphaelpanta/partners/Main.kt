package com.github.raphaelpanta.partners

import MongoDbPartnersRepository
import com.github.raphaelpanta.partners.service.PartnersAppService
import io.javalin.Javalin

fun main() {
    val app = Javalin.create().start(7000)

    val partnersRepository = MongoDbPartnersRepository()
    val partnerService = PartnersAppService(partnersRepository)
    val partnerController = PartnerController(partnerService)
    val partnersRoutes = PartnersRoutes(partnerController)

    app.routes(partnersRoutes::routes)

}