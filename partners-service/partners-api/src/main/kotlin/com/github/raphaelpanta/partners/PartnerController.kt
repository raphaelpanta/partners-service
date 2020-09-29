package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.PartnerService
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

class PartnerController(private val partnerService: PartnerService) {

    fun create(context: Context) {

        context.bodyAsClass(CreatePartnerRequest::class.java)
                .let(partnerService::create)?.apply {
                    context.status(201)
                    context.json(this)
                } ?: run {
            context.status(400)
            context.json("""{
                | "message": "Could not save partner"
                |}""".trimMargin())
        }
    }
}