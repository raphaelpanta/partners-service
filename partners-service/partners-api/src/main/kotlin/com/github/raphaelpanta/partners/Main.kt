package com.github.raphaelpanta.partners

import io.javalin.Javalin

fun main() {
    val app = Javalin.create().start(7000)

    val partnerController = PartnerController
    val partnersRoutes = PartnersRoutes(partnerController)

    app.routes(partnersRoutes::routes)

}