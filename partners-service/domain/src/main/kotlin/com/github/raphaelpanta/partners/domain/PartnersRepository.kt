package com.github.raphaelpanta.partners.domain

interface PartnersRepository {
    fun create(partner: Partner): Partner
}