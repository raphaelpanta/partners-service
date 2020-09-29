package com.github.raphaelpanta.partners.service

enum class ErrorType {
    Validation,
    Error
}

data class InvalidResult(val errors: List<String>, val type: ErrorType = ErrorType.Validation)