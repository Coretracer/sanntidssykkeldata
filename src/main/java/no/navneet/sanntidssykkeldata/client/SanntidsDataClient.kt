package no.navneet.sanntidssykkeldata.client

import io.vavr.control.Try
import no.navneet.sanntidssykkeldata.api.external.Stasjoner.OsloByStasjoner
import no.navneet.sanntidssykkeldata.api.external.Tilgjengelighet.OsloByTilgjengelighet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.client.Client
import javax.ws.rs.core.MediaType

class SanntidsDataClient(
    private val client: Client,
    private val sanntidsDataBaseUrl: String
) {

    fun getTilgjengeligheterDataViaApi(): OsloByTilgjengelighet? {
        val result = Try.ofSupplier { internalGetTilgjengeligheterViaApi() }
        return result.getOrElseGet {
            logger.warn("Failed retrieving Tilgjengelighet data", it)
            null
        }
    }

    fun getStasjonerDataViaApi(): OsloByStasjoner? {
        val result = Try.ofSupplier { internalGetStasjonerViaApi() }
        return result.getOrElseGet {
            logger.warn("Failed retrieving Stasjoner data", it)
            null
        }
    }

    private fun internalGetTilgjengeligheterViaApi(): OsloByTilgjengelighet {
        return client
            .target("$sanntidsDataBaseUrl/$STASJON_STATUS_PATH")
            .request()
            .header("Client-Identifier", "IDENTIFIER")
            .accept(MediaType.APPLICATION_JSON)
            .get(OsloByTilgjengelighet::class.java)
    }

    private fun internalGetStasjonerViaApi(): OsloByStasjoner {
        return client
            .target("$sanntidsDataBaseUrl/$STASJON_INFORMASJON_PATH")
            .request()
            .header("Client-Identifier", "IDENTIFIER")
            .accept(MediaType.APPLICATION_JSON)
            .get(OsloByStasjoner::class.java)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SanntidsDataClient::class.java)
        const val STASJON_INFORMASJON_PATH = "station_information.json"
        const val STASJON_STATUS_PATH = "station_status.json"
    }
}
