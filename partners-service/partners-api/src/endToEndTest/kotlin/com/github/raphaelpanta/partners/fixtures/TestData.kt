package com.github.raphaelpanta.partners.fixtures

import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Point
import com.github.raphaelpanta.partners.service.PartnerResponse

val createPartnerResponse = PartnerResponse(
        id = "",
        "Adega da Cerveja - Pinheiros",
        "Zé da Silva",
        "1432132123891/0002",
        MultiPolygon(

                listOf(
                        listOf(
                                listOf(
                                        listOf(30.0f, 20.0f),
                                        listOf(45.0f, 40.0f),
                                        listOf(10.0f, 40.0f),
                                        listOf(30.0f, 20.0f)
                                )
                        ),
                        listOf(
                                listOf(
                                        listOf(15.0f, 5.0f),
                                        listOf(40.0f, 10.0f),
                                        listOf(10.0f, 20.0f),
                                        listOf(5.0f, 10.0f),
                                        listOf(15.0f, 5.0f)
                                )
                        )
                ),
                "MultiPolygon"
        ),
        Point(listOf(-46.57421, -21.785741), "Point")
)