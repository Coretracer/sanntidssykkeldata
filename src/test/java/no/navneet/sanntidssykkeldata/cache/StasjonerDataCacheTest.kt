package no.navneet.sanntidssykkeldata.cache

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport
import no.navneet.sanntidssykkeldata.SanntidsSykkelDataApplication
import no.navneet.sanntidssykkeldata.client.SanntidsDataClient
import no.navneet.sanntidssykkeldata.configuration.SanntidsSykkelDataConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.kotlin.await
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File

@ExtendWith(DropwizardExtensionsSupport::class)
class StasjonerDataCacheTest {

    @JvmField
    val sanntidsDataWireMockServer = WireMockServer(
        WireMockConfiguration.wireMockConfig().dynamicPort()
    )

    @BeforeEach
    fun beforeEach() {
        sanntidsDataWireMockServer.start()
        StasjonerApiStub.stubStasjonerData(sanntidsDataWireMockServer)
    }

    @AfterEach
    fun afterEach() {
        sanntidsDataWireMockServer.stop()
    }

    @Test
    fun testLoadOfCache() {

        val cache = StasjonerDataCache(
            SanntidsDataClient(
                EXT.client(),
                "http://localhost:${sanntidsDataWireMockServer.port()}"
            )
        )
        await.untilAsserted {
            sanntidsDataWireMockServer.verify(WireMock.getRequestedFor(urlEqualTo("/${SanntidsDataClient.STASJON_INFORMASJON_PATH}")))
        }

        assertThat(cache.getOsloByStasjoner()).isNotNull
    }

    @Test
    fun verifyCachingByMultipleCallDoesNotInvokeMultipleApiRequest() {

        val cache = StasjonerDataCache(
            SanntidsDataClient(
                EXT.client(),
                "http://localhost:${sanntidsDataWireMockServer.port()}"
            )
        )
        await.untilAsserted {
            sanntidsDataWireMockServer.verify(WireMock.getRequestedFor(urlEqualTo("/${SanntidsDataClient.STASJON_INFORMASJON_PATH}")))
        }

        assertThat(cache.getOsloByStasjoner()).isNotNull
        assertThat(cache.getOsloByStasjoner()).isNotNull
        assertThat(cache.getOsloByStasjoner()).isNotNull
        assertThat(cache.getOsloByStasjoner()).isNotNull

        sanntidsDataWireMockServer.verify(
            1,
            WireMock.getRequestedFor(urlEqualTo("/${SanntidsDataClient.STASJON_INFORMASJON_PATH}"))
        )
    }

    companion object {

        @JvmStatic
        private val EXT: DropwizardAppExtension<SanntidsSykkelDataConfiguration> =
            DropwizardAppExtension<SanntidsSykkelDataConfiguration>(
                SanntidsSykkelDataApplication::class.java,
                File("src/main/resources/config.yml").toPath().toAbsolutePath().toString()
            )
    }
}
