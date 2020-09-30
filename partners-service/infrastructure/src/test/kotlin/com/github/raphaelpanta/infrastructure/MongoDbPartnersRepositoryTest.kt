package com.github.raphaelpanta.infrastructure

import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import com.github.raphaelpanta.partners.domain.Point
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.UUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoDbPartnersRepositoryTest {
    private val monogDbDriver = MongoDbDriver()
    private val repository: PartnersRepository = MongoDbPartnersRepository(monogDbDriver.mongoClient)

    private val testPartner = Partner(
            tradingName = "Adega da Cerveja - Pinheiros",
            ownerName = "Zé da Silva",
            document = "1432132123891/0001",
            coverageArea = MultiPolygon(
                    listOf(
                            listOf(
                                    listOf(
                                            listOf(
                                                    listOf(30.0, 20.0),
                                                    listOf(45.0, 40.0),
                                                    listOf(10.0, 40.0),
                                                    listOf(30.0, 20.0)
                                            )
                                    )
                            ),
                            listOf(
                                    listOf(
                                            listOf(
                                                    listOf(15.0, 5.0),
                                                    listOf(40.0, 10.0),
                                                    listOf(10.0, 20.0),
                                                    listOf(5.0, 10.0),
                                                    listOf(15.0, 5.0)
                                            )
                                    )
                            )
                    ),
                    "MultiPolygon"
            ),
            Point(listOf(-46.57421, -21.785741), "Point")
    )

    @Test
    fun `should add a partner successfully`() {
        val (partner) = repository.create(testPartner)
        expectThat(partner).isEqualTo(testPartner)
    }

    @Test
    fun `should find partner`() {
        val insertResult = repository.create(testPartner)

        val result = insertResult.component1()?.id.let { repository.find(UUID.fromString(it)) }.component1()

        expectThat(result).isEqualTo(testPartner)
    }


    @Test
    fun `should not find partner`() {

        val id = "7b2a0415-ae18-4f61-b4b1-51eb72ae9032"

        val result = repository.find(UUID.fromString(id))

        expectThat(result) {
            get { component1() } isEqualTo null
            get { component2() } isEqualTo null
        }
    }


    @AfterAll
    fun teardown() {
        monogDbDriver.stop()
    }

}