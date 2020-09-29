import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.raphaelpanta.partners.service.CreatePartnerKonformValidator
import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.InvalidResult
import org.geojson.MultiPolygon
import org.geojson.Point
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class CreatePartnerValidationTest {

    private val validPartnerRequest = CreatePartnerRequest(
            "tradingName",
            "ownerName",
            "1432132123891/0001",
            MultiPolygon(),
            Point()
    )

    private val createPartnerValidator = CreatePartnerKonformValidator()

    @Test
    fun `should be valid`() {
        val result = createPartnerValidator.validate(validPartnerRequest)

        expectThat(result as Ok<CreatePartnerRequest>) {
            isA<Ok<CreatePartnerRequest>>()
            get { value } isEqualTo validPartnerRequest
        }
    }

    @TestFactory
    fun `should be not valid`() = listOf(
            validPartnerRequest.copy("") to ".tradingName - must have at least 1 characters",
            validPartnerRequest.copy("x".repeat(256)) to ".tradingName - must have at most 255 characters",
            validPartnerRequest.copy(ownerName = "") to ".ownerName - must have at least 1 characters",
            validPartnerRequest.copy(ownerName = "x".repeat(256)) to ".ownerName - must have at most 255 characters",
            validPartnerRequest.copy(document = "") to ".document - should be in brazilian legal document. Ex: 1432132123891/0001",

            ).map { (invalid, message) ->
        dynamicTest("Partner should be invalid for this reason: $message") {
            val result = createPartnerValidator.validate(invalid)

            expectThat(result as Err<InvalidResult>) {
                isA<Err<InvalidResult>>()
                get { error.errors } isEqualTo listOf(message)
            }
        }
    }
}