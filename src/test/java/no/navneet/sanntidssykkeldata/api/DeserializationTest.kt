package no.navneet.sanntidssykkeldata.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.navneet.sanntidssykkeldata.api.external.Stasjoner.OsloByStasjoner
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DeserializationTest {

    @Test
    fun testJa() {
        val osloByStasjoner: OsloByStasjoner = jacksonObjectMapper().readValue(
            """
                {
                    "data": {
                        "stations": [
                            {
                                "address": "Olav Vs Gate 5",  
                                "capacity": 15,
                                "lat": 59.913150534075015,
                                "lon": 10.732281291700133,
                                "name": "Klingenberggata",
                                "station_id": "2280"
                            }
                        ]
                    },
                    "last_updated": 1615855271,
                    "ttl": 10
                }
            """.trimIndent()
        )
        assertThat(osloByStasjoner).isNotNull
        assertThat(osloByStasjoner.ttl).isEqualTo(10)
    }
}
