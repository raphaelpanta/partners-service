package com.github.raphaelpanta.partners

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.plugin.openapi.dsl.documented

class PartnersRoutes(private val partnerController: PartnerController) {

    fun routes() = path("/partners") {
        post("/", documented(createPartner, partnerController::create))
        get("/:id", documented(findById, partnerController::find))
        get("/:long/:lat", documented(findNearestPartner, partnerController::nearest))
    }
}
