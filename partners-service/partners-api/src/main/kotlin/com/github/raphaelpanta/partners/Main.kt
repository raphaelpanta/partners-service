package com.github.raphaelpanta.partners

import io.javalin.Javalin
import org.geojson.LngLatAlt
import org.geojson.MultiPolygon
import org.geojson.Point
import org.geojson.Polygon

fun main() {
    print("application started")

    val app = Javalin.create().start(7000)

    app.post("/partners") {
        it.status(201)
        it.json(CreatePartnerRequest(
                "Adega da Cerveja - Pinheiros",
                "Zé da Silva",
                "1432132123891/0001",
                MultiPolygon()
                        .add(
                                Polygon(
                                        LngLatAlt(30.0, 20.0),
                                        LngLatAlt(45.0, 40.0),
                                        LngLatAlt(10.0, 40.0),
                                        LngLatAlt(30.0, 20.0)
                                )
                        )
                        .add(
                                Polygon(
                                        LngLatAlt(15.0, 5.0),
                                        LngLatAlt(40.0, 10.0),
                                        LngLatAlt(10.0, 20.0),
                                        LngLatAlt(5.0, 10.0),
                                        LngLatAlt(15.0, 5.0)
                                )),
                Point(-46.57421, -21.785741)
        ))
    }
}