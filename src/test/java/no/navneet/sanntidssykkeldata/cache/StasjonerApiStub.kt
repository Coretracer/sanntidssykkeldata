package no.navneet.sanntidssykkeldata.cache

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import no.navneet.sanntidssykkeldata.api.external.Stasjoner.Stasjon
import no.navneet.sanntidssykkeldata.client.SanntidsDataClient

object StasjonerApiStub {
    val stations = Stasjon(
        stationId = "2280",
        name = "Klingenberggata",
        address = "Olav Vs Gate 5",
        lat = 59.913150534075015,
        lon = 10.732281291700133,
        capacity = 15
    )
    fun stubStasjonerData(
        sanntidsDataServer: WireMockServer
    ) {
        sanntidsDataServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/${SanntidsDataClient.STASJON_INFORMASJON_PATH}"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            """
                                    {
                                        "last_updated": 1615855271,
                                        "ttl": 10,
                                         "data": {
                                            "stations": [  ${jacksonObjectMapper().registerKotlinModule().writeValueAsString(stations)} ]
                                         }
                                    }
                                   """
                        )
                )
        )
    }
}
