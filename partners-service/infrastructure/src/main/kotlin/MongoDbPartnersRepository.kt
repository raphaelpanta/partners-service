import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import org.geojson.LngLatAlt
import org.geojson.MultiPolygon
import org.geojson.Point
import org.geojson.Polygon

class MongoDbPartnersRepository : PartnersRepository {
    override fun create(partner: Partner): Partner {
        return Partner(1,
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
                Point(-46.57421, -21.785741))
    }
}