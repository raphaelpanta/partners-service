package com.github.raphaelpanta.partners

import cc.vileda.openapi.dsl.info
import cc.vileda.openapi.dsl.openapiDsl
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions

fun openApiOptions() = OpenApiPlugin(
        OpenApiOptions {
            openapiDsl {
                info {
                    version = "1.0.0"
                    description = "Partners Service"
                    title = "Partners API"
                }
            }
        }.apply {
            path("/swagger-docs")
            swagger(SwaggerOptions("/swagger-ui"))
        }
)