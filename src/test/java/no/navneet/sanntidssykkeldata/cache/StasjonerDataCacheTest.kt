package no.navneet.sanntidssykkeldata.cache

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import no.navneet.sanntidssykkeldata.client.SanntidsDataClient
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.kotlin.await
import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.JerseyClientBuilder
import org.junit.Rule
import org.junit.Test
import javax.ws.rs.client.Client

class StasjonerDataCacheTest {
    @Rule
    @JvmField
    val sanntidsDataWireMockServer = WireMockRule(
        WireMockConfiguration.wireMockConfig().dynamicPort()
    )

    private val client = getClient()

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
            WireMock.verify(WireMock.getRequestedFor(urlEqualTo("/${SanntidsDataClient.STASJON_INFORMASJON_PATH}")))
        }

        assertThat(cache.getOsloByStasjoner()).isNotNull
    }

    fun getClient(): Client {
        val configuration = ClientConfig().configuration
            .register(jacksonObjectMapper().registerKotlinModule())
        return JerseyClientBuilder().withConfig(configuration).build()
    }
}
