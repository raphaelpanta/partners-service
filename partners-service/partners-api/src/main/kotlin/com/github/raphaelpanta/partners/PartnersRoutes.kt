package com.github.raphaelpanta.partners

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post

class PartnersRoutes(private val partnerController: PartnerController) {

    fun routes() = path("/partners") {
        post("/", partnerController::create)
        get("/:id", partnerController::find)
    }
}
