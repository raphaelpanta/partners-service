package com.github.raphaelpanta.infrastructure

import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import org.geojson.LngLatAlt
import org.geojson.MultiPolygon
import org.geojson.Point
import org.geojson.Polygon
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoDbPartnersRepositoryTest {
    private val monogDbDriver = MongoDbDriver()
    private val repository: PartnersRepository = MongoDbPartnersRepository(monogDbDriver.mongoClient)

    @Test
    fun `should add a partner successfully`() {
        val partner = Partner(
                tradingName = "Adega da Cerveja - Pinheiros",
                ownerName = "Zé da Silva",
                document = "1432132123891/0001",
                coverageArea = MultiPolygon()
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
                address = Point(-46.57421, -21.785741))

        val result = repository.create(partner)

        expectThat(result).isEqualTo(partner)
    }


    @AfterAll
    fun teardown() {
        monogDbDriver.stop()
    }

}