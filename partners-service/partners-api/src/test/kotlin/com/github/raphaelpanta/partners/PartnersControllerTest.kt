package com.github.raphaelpanta.partners

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.raphaelpanta.partners.HttpCodes.badRequestErrorCode
import com.github.raphaelpanta.partners.HttpCodes.createdCode
import com.github.raphaelpanta.partners.HttpCodes.internalServerErrorCode
import com.github.raphaelpanta.partners.HttpCodes.okCode
import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.InvalidResult.InternalErrorResult
import com.github.raphaelpanta.partners.service.InvalidResult.ValidationErrorResult
import com.github.raphaelpanta.partners.service.PartnerResponse
import com.github.raphaelpanta.partners.service.PartnerService
import io.javalin.http.Context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test

class PartnersControllerTest {

    @Test
    fun `Should create a partner successfully`() {
        val context = mockk<Context>()
        val partner = mockk<CreatePartnerRequest>()
        val partnerResp = mockk<Ok<PartnerResponse>>()
        val partnerResponse = mockk<PartnerResponse>()
        val partnerService = mockk<PartnerService>()
        val partnerController = PartnerController(partnerService)

        every { context.bodyAsClass(CreatePartnerRequest::class.java) } returns partner
        every { context.status(createdCode) } returns context
        every { context.json(partnerResponse) } returns context
        every { partnerService.create(partner) } returns partnerResp
        every { partnerResp.value } returns partnerResponse

        partnerController.create(context)

        verifyOrder {
            context.bodyAsClass(CreatePartnerRequest::class.java)
            partnerService.create(partner)
            partnerResp.value
            context.status(createdCode)
            context.json(partnerResponse)
        }

        confirmVerified(context, partner, partnerResp, partnerService)
    }

    @Test
    fun `Should not create a partner successfully due validation`() {
        val context = mockk<Context>()
        val partner = mockk<CreatePartnerRequest>()
        val partnerService = mockk<PartnerService>()
        val partnerController = PartnerController(partnerService)
        val result = Err(ValidationErrorResult(listOf("a message")))

        every { context.bodyAsClass(CreatePartnerRequest::class.java) } returns partner
        every { context.status(badRequestErrorCode) } returns context
        every { context.json(result.error) } returns context
        every { partnerService.create(partner) } returns result

        partnerController.create(context)

        verifyOrder {
            context.bodyAsClass(CreatePartnerRequest::class.java)
            partnerService.create(partner)
            context.status(badRequestErrorCode)
            context.json(result.error)
        }

        confirmVerified(context, partner, partnerService)
    }

    @Test
    fun `Should not create a partner successfully due a internal server error`() {
        val context = mockk<Context>()
        val partner = mockk<CreatePartnerRequest>()
        val partnerService = mockk<PartnerService>()
        val partnerController = PartnerController(partnerService)
        val result = Err(InternalErrorResult(listOf("a message")))

        every { context.bodyAsClass(CreatePartnerRequest::class.java) } returns partner
        every { context.status(internalServerErrorCode) } returns context
        every { context.json(result.error) } returns context
        every { partnerService.create(partner) } returns result

        partnerController.create(context)

        verifyOrder {
            context.bodyAsClass(CreatePartnerRequest::class.java)
            partnerService.create(partner)
            context.status(internalServerErrorCode)
            context.json(result.error)
        }

        confirmVerified(context, partner, partnerService)
    }

}

class PartnerFindTest {
    @Test
    fun `should find a partner by its id`() {
        val id = "c9361281-ea43-42f0-8573-ec9cce65852d"
        val context = mockk<Context>()
        val partner = mockk<PartnerResponse>()
        val partnerService = mockk<PartnerService>()
        val partnerResponse = mockk<Ok<PartnerResponse>>()
        val partnerController = PartnerController(partnerService)

        every { partnerService.find(id) } returns partnerResponse
        every { context.pathParam("id") } returns id
        every { context.status(okCode) } returns context
        every { context.json(partner) } returns context
        every { partnerResponse.value } returns partner

        partnerController.find(context)

        verifyOrder {
            context.pathParam(key = "id")
            partnerService.find(id)
            partnerResponse.value
            context.status(okCode)
            context.json(partner)
        }

        confirmVerified(partnerResponse, partnerService, context, partner, partnerResponse)
    }

    @Test
    fun `should not find a partner by its id`() {
        val id = "c9361281-ea43-42f0-8573-ec9cce65852d"
        val context = mockk<Context>()
        val partner = mockk<PartnerResponse>()
        val partnerService = mockk<PartnerService>()
        val partnerResponse = mockk<Err<ValidationErrorResult>>()
        val validationErrorResult = ValidationErrorResult(listOf("Could not find any partner by id"))
        val partnerController = PartnerController(partnerService)

        every { partnerService.find(id) } returns partnerResponse
        every { context.pathParam("id") } returns id
        every { context.status(badRequestErrorCode) } returns context
        every { partnerResponse.error } returns validationErrorResult
        every { context.json(validationErrorResult) } returns context

        partnerController.find(context)

        verifyOrder {
            context.pathParam(key = "id")
            partnerService.find(id)
            partnerResponse.error
            context.status(badRequestErrorCode)
            context.json(validationErrorResult)
        }

        confirmVerified(partnerResponse, partnerService, context, partner, partnerResponse)
    }

    @Test
    fun `should not find a partner by its id from internal errors`() {
        val id = "c9361281-ea43-42f0-8573-ec9cce65852d"
        val context = mockk<Context>()
        val partner = mockk<PartnerResponse>()
        val partnerService = mockk<PartnerService>()
        val partnerResponse = mockk<Err<InternalErrorResult>>()
        val internalErrorResult = InternalErrorResult(listOf("Could not find any partner by id"))
        val partnerController = PartnerController(partnerService)

        every { partnerService.find(id) } returns partnerResponse
        every { context.pathParam("id") } returns id
        every { context.status(internalServerErrorCode) } returns context
        every { partnerResponse.error } returns internalErrorResult
        every { context.json(internalErrorResult) } returns context

        partnerController.find(context)

        verifyOrder {
            context.pathParam(key = "id")
            partnerService.find(id)
            partnerResponse.error
            context.status(internalServerErrorCode)
            context.json(internalErrorResult)
        }

        confirmVerified(partnerResponse, partnerService, context, partner, partnerResponse)
    }
}

class NearestPartnerTest {
    @Test
    fun `should find a partner`() {
        val context = mockk<Context>()
        val service = mockk<PartnerService>()
        val result = mockk<Ok<PartnerResponse>>()
        val response = mockk<PartnerResponse>()
        val controller = PartnerController(service)

        val long = "2.0"
        val lat = "3.0"
        val coordinates = 2.0f to 3.0f

        every { context.pathParam("long") } returns long
        every { context.pathParam("lat") } returns lat
        every { context.status(okCode) } returns context
        every { context.json(response) } returns context
        every { result.value } returns response
        every { service.nearestPartnerForLocation(coordinates) } returns result

        controller.nearest(context)

        verifyOrder {
            context.pathParam("long")
            context.pathParam("lat")
            service.nearestPartnerForLocation(coordinates)
            result.value
            context.status(okCode)
            context.json(response)
        }

        confirmVerified(context, service, response, response)
    }
}

object HttpCodes {
    const val internalServerErrorCode = 500
    const val badRequestErrorCode = 400
    const val okCode = 200
    const val createdCode = 201
}
