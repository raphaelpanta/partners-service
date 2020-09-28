package com.github.raphaelpanta.partners

import io.javalin.Javalin

fun main() {
    val app = Javalin.create().start(7000)

    app.routes(
            PartnersRoutes::routes
    )
}