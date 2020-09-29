package com.github.raphaelpanta.partners

import com.github.michaelbull.result.fold
import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.ErrorType
import com.github.raphaelpanta.partners.service.PartnerService
import io.javalin.http.Context

class PartnerController(private val partnerService: PartnerService) {

    fun create(context: Context) {

        context.bodyAsClass(CreatePartnerRequest::class.java)
                .let(partnerService::create)
                .fold({
                    context.status(201)
                    context.json(it)
                }) {
                    context.status(if (it.type == ErrorType.Error) 500 else 400)
                    context.json(it)
                }
    }
}