package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.InvalidResult.InternalErrorResult
import com.github.raphaelpanta.partners.service.InvalidResult.ValidationErrorResult
import com.github.raphaelpanta.partners.service.PartnerResponse
import io.javalin.plugin.openapi.dsl.document
import java.util.UUID

val createPartner = document()
        .operation {
            it.description("Creates a partner")
            it.summary("Creates a Partner with their info and their location and coverage area")
            it.deprecated = false

        }
        .body<CreatePartnerRequest>()
        .json<PartnerResponse>("200")
        .json<InternalErrorResult>("500")
        .json<ValidationErrorResult>("400")

val findById = document()
        .operation {
            it.description("Find Partner by its ID")
            it.summary("Find a Partner by ids UUID id.")
            it.deprecated = false

        }
        .pathParam<UUID>("id")
        .json<CreatePartnerRequest>("200")
        .json<InternalErrorResult>("500")
        .json<ValidationErrorResult>("400")

val findNearestPartner = document()
        .operation {
            it.description("Find nearest Partner")
            it.summary("Find nearest partner given a location that under partner's coverage area.")
            it.deprecated = false

        }
        .pathParam<Float>("long")
        .pathParam<Float>("lat")
        .json<CreatePartnerRequest>("200")
        .json<InternalErrorResult>("500")
        .json<ValidationErrorResult>("400")