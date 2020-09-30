package com.github.raphaelpanta.partners.service

sealed class InvalidResult(open val errors: List<String>) {
    data class ValidationErrorResult(override val errors: List<String>) : InvalidResult(errors)
    data class InternalErrorResult(override val errors: List<String>) : InvalidResult(errors)
}
