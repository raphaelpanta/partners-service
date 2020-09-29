package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.CreatePartnerResponse
import com.github.raphaelpanta.partners.service.PartnerService
import io.javalin.http.Context
import io.mockk.*
import org.junit.jupiter.api.Test

object PartnersControllerTest {

    @Test
    fun `Should create a partner successfully`() {
        val context = mockk<Context>()
        val partner = mockk<CreatePartnerRequest>()
        val partnerResp = mockk<CreatePartnerResponse>()
        val partnerService = mockk<PartnerService>()
        val partnerController = PartnerController(partnerService)

        every { context.bodyAsClass(CreatePartnerRequest::class.java) } returns partner
        every { context.status(201) } returns context
        every { context.json(partnerResp) } returns context
        every { partnerService.create(partner) } returns partnerResp

        partnerController.create(context)

        verifyOrder {
            context.bodyAsClass(CreatePartnerRequest::class.java)
            partnerService.create(partner)
            context.status(201)
            context.json(partnerResp)
        }

        confirmVerified(context, partner, partnerResp, partnerService)
    }
}