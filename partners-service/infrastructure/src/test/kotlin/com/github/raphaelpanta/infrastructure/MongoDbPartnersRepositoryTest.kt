package com.github.raphaelpanta.infrastructure

import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import com.github.raphaelpanta.partners.domain.Point
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
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

    @BeforeAll
    fun addTest() {
        repository.create(testPartner)
    }

    @Test
    @Order(0)
    fun `should add a partner successfully`() {
        val test = testPartner.copy(
                id = UUID.randomUUID().toString(),
                document = "1432132123891/0002",
                coverageArea = MultiPolygon(
                        listOf(
                                listOf(
                                        listOf(
                                                listOf(40.0f, 13.0f),
                                                listOf(36.0f, 13.0f),
                                                listOf(36.0f, 10.0f),
                                                listOf(40.0f, 10.0f),
                                                listOf(40.0f, 13.0f)
                                        )
                                )
                        ), type = "MultiPolygon"
                )
        )

        val (partner) = repository.create(test)
        expectThat(partner).isEqualTo(test)
    }

    @Test
    @Order(1)
    fun `should find partner`() {

        val (result) = repository.find(UUID.fromString(testPartner.id))

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

    @Test
    fun `should find nearest partner`() {
        val result = repository.searchNearestPartner(30.0f to 20.0f)

        result.component2().let(::print)
        expectThat(result.component1()).isEqualTo(testPartner)
    }

    @Test
    fun `should not find nearest partner`() {
        val result = repository.searchNearestPartner(20.0f to 20.0f)

        result.component2().let(::print)
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
