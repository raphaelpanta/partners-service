package com.github.raphaelpanta.partners.service

interface PartnerService {
    fun create(createPartnerRequest: CreatePartnerRequest): CreatePartnerResponse
}