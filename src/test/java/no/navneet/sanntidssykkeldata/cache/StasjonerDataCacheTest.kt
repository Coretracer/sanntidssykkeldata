package no.navneet.sanntidssykkeldata.cache

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import no.navneet.sanntidssykkeldata.client.SanntidsDataClient
import org.awaitility.kotlin.await
import org.glassfish.jersey.client.JerseyClientBuilder
import org.junit.Rule
import org.junit.Test
import javax.ws.rs.client.Client
import org.assertj.core.api.Assertions.assertThat

class StasjonerDataCacheTest {
    @Rule
    @JvmField
    val sanntidsDataWireMockServer = WireMockRule(
        WireMockConfiguration.wireMockConfig().dynamicPort()
    )

    private val client: Client = JerseyClientBuilder().build()

    init {
        StasjonerApiStub.stubStasjonerData(sanntidsDataWireMockServer)
    }


    @Test
    fun testLoadOfCache() {
        val cache = StasjonerDataCache(
            SanntidsDataClient(
                client,
                "http://localhost:${sanntidsDataWireMockServer.port()}"
            )
        )
        await.untilAsserted {
            WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/${SanntidsDataClient.STASJON_INFORMASJON_PATH}")))
        }


        assertThat(cache.getOsloByStasjoner()).isNotNull

    }
}