package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post

class PartnersRoutes(private val partnerController: PartnerController) {

    fun routes() = path("/partners") {
        post("/") {
           partnerController.create(it)
        }
    }
}
