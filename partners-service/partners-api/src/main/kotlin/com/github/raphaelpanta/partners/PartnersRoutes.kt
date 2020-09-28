package com.github.raphaelpanta.partners

import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post

class PartnersRoutes(private val partnerController: PartnerController) {

    fun routes() = path("/partners") {
        post("/") {
            val request = it.bodyAsClass(CreatePartnerRequest::class.java)
            val response = partnerController.create(request)
            it.status(201)
            it.json(response)
        }
    }
}
