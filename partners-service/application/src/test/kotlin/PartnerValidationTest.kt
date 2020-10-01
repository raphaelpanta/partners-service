import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Point
import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.InvalidResult
import com.github.raphaelpanta.partners.service.InvalidResult.ValidationErrorResult
import com.github.raphaelpanta.partners.service.PartnerKonformValidator
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import java.util.UUID

class PartnerValidationTest {

    private val testData = CreatePartnerRequest(
            "tradingName",
            "ownerName",
            "1432132123891/0001",
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

    private val partnerValidator = PartnerKonformValidator()

    @Test
    fun `should be valid`() {
        val result = partnerValidator.validate(testData)

        expectThat(result as Ok<CreatePartnerRequest>) {
            isA<Ok<CreatePartnerRequest>>()
            get { value } isEqualTo testData
        }
    }

    @TestFactory
    fun `should be not valid`() = listOf(
            testData.copy("") to ".tradingName - must have at least 1 characters",
            testData.copy("x".repeat(256)) to ".tradingName - must have at most 255 characters",
            testData.copy(ownerName = "") to ".ownerName - must have at least 1 characters",
            testData.copy(ownerName = "x".repeat(256)) to ".ownerName - must have at most 255 characters",
            testData.copy(coverageArea = MultiPolygon(testData.coverageArea.coordinates, type = ""))
                    to ".coverageArea.type - Should be a valid MultiPolygon geoJSON type",
            testData.copy(address = Point(testData.address.coordinates, ""))
                    to ".address.type - Should be a valid Point geoJson type",
            testData.copy(address = Point(listOf(-1.0f), "Point"))
                    to ".address.coordinates - Should be a valid Point geoJson coordinates (long, lat)",
            testData.copy(address = Point(listOf(-1.0f, -1.0f, -1.0f), "Point"))
                    to ".address.coordinates - Should be a valid Point geoJson coordinates (long, lat)"
    ).map { (invalid, message) ->
        dynamicTest("Partner should be invalid for this reason: $message") {
            val result = partnerValidator.validate(invalid)

            expectThat(result as Err<InvalidResult>) {
                isA<Err<InvalidResult>>()
                get { error.errors } isEqualTo listOf(message)
            }
        }
    }

    @Test
    fun `should be a UUID string id`() {
        val validationResult = partnerValidator.validate("7b2a0415-ae18-4f61-b4b1-51eb72ae9032")
        expectThat(validationResult) {
            isA<Ok<UUID>>()
        }
    }

    @Test
    fun `should be an invalid UUID id`() {
        expectThat(partnerValidator.validate("2")) {
            isA<Err<ValidationErrorResult>>()
        }
    }
}
