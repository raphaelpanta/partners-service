package com.github.raphaelpanta.partners

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.CreatePartnerResponse
import com.github.raphaelpanta.partners.service.InvalidResult
import com.github.raphaelpanta.partners.service.PartnerService
import io.javalin.http.Context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test

object PartnersControllerTest {

    @Test
    fun `Should create a partner successfully`() {
        val context = mockk<Context>()
        val partner = mockk<CreatePartnerRequest>()
        val partnerResp = mockk<Ok<CreatePartnerResponse>>()
        val partnerResponse = mockk<CreatePartnerResponse>()
        val partnerService = mockk<PartnerService>()
        val partnerController = PartnerController(partnerService)

        every { context.bodyAsClass(CreatePartnerRequest::class.java) } returns partner
        every { context.status(201) } returns context
        every { context.json(partnerResponse) } returns context
        every { partnerService.create(partner) } returns partnerResp
        every { partnerResp.value } returns partnerResponse

        partnerController.create(context)

        verifyOrder {
            context.bodyAsClass(CreatePartnerRequest::class.java)
            partnerService.create(partner)
            partnerResp.value
            context.status(201)
            context.json(partnerResponse)
        }

        confirmVerified(context, partner, partnerResp, partnerService)
    }

    @Test
    fun `Should not create a partner successfully`() {
        val context = mockk<Context>()
        val partner = mockk<CreatePartnerRequest>()
        val partnerService = mockk<PartnerService>()
        val partnerController = PartnerController(partnerService)
        val result = Err(InvalidResult(listOf("a message")))

        every { context.bodyAsClass(CreatePartnerRequest::class.java) } returns partner
        every { context.status(400) } returns context
        every { context.json(result.error) } returns context
        every { partnerService.create(partner) } returns result

        partnerController.create(context)

        verifyOrder {
            context.bodyAsClass(CreatePartnerRequest::class.java)
            partnerService.create(partner)
            context.status(400)
            context.json(result.error)
        }

        confirmVerified(context, partner, partnerService)
    }
}