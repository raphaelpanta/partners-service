package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post

class PartnersRoutes(private val partnerController: PartnerController) {

    fun routes() = path("/partners") {
        post("/") {
            val request = it.bodyAsClass(CreatePartnerRequest::class.java)
            partnerController.create(request)?.apply {
                it.status(201)
                it.json(this)
            } ?: run {
                it.status(400)
            }
        }
    }
}
