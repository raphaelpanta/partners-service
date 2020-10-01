package com.github.raphaelpanta.partners

import com.github.michaelbull.result.fold
import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.InvalidResult
import com.github.raphaelpanta.partners.service.InvalidResult.InternalErrorResult
import com.github.raphaelpanta.partners.service.InvalidResult.ValidationErrorResult
import com.github.raphaelpanta.partners.service.PartnerService
import io.javalin.http.Context

class PartnerController(private val partnerService: PartnerService) {

    fun create(context: Context) {
        context.bodyAsClass(CreatePartnerRequest::class.java)
                .let(partnerService::create)
                .fold({
                    context.status(createdCode)
                    context.json(it)
                }) {
                    context.status(when (it) {
                        is InternalErrorResult -> internalServerErrorCode
                        is ValidationErrorResult -> badRequestErrorCode
                    })
                    context.json(it)
                }
    }

    fun find(context: Context) {
        context.pathParam("id")
                .let(partnerService::find)
                .fold({
                    context.status(okCode)
                    context.json(it)
                }) {
                    context.status(when (it) {
                        is InternalErrorResult -> internalServerErrorCode
                        is ValidationErrorResult -> badRequestErrorCode
                    })
                    context.json(it)
                }
    }

    fun nearest(context: Context) {
        context.run {
            pathParam("long").toFloat() to pathParam("lat").toFloat()
        }
                .let(partnerService::nearestPartnerForLocation)
                .fold(
                        {
                            context.status(okCode)
                            context.json(it)
                        }
                ) { context.handleError(it) }
    }

    companion object {
        const val internalServerErrorCode = 500
        const val badRequestErrorCode = 400
        const val okCode = 200
        const val createdCode = 201

        fun Context.handleError(invalidResult: InvalidResult) {
            status(
                    when (invalidResult) {
                        is InternalErrorResult -> internalServerErrorCode
                        is ValidationErrorResult -> badRequestErrorCode
                    }
            )

            json(invalidResult)
        }
    }
}
