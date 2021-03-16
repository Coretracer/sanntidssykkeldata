package no.navneet.sanntidssykkeldata.client

import no.navneet.sanntidssykkeldata.api.external.Stasjoner.Stasjon
import no.navneet.sanntidssykkeldata.api.external.Tilgjengelighet.Tilgjengelighet
import no.navneet.sanntidssykkeldata.api.internal.SykkelStativ
import no.navneet.sanntidssykkeldata.cache.StasjonerDataCache
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class SykkelStativService(private val sanntidsDataClient: SanntidsDataClient) {

    val stasjonerDataCache = StasjonerDataCache(sanntidsDataClient)

    fun getSykkelStativ(): List<SykkelStativ> {
        val stasjoner = stasjonerDataCache
            .getOsloByStasjoner()
            .data
            .stations

        val stasjonIdToTilgjengelighetMap: Map<String, Tilgjengelighet> = getTilgjengeligheter()
            .associateBy { it.stationId }

        return stasjoner.map { stasjon ->
            createSykkelStativ(stasjon, stasjonIdToTilgjengelighetMap[stasjon.stationId])
        }
    }

    private fun getTilgjengeligheter() = sanntidsDataClient
        .getTilgjengeligheterDataViaApi()?.data?.stations ?: emptyList()

    private fun createSykkelStativ(
        stasjon: Stasjon,
        tilgjengelighet: Tilgjengelighet?
    ) = SykkelStativ(
        stasjon.stationId,
        tilgjengelighet?.let { epochToLocalDateTime(it) } ?: LocalDateTime.now(),
        stasjon.name,
        stasjon.address,
        stasjon.capacity,
        tilgjengelighet?.numBikesAvailable ?: 0,
        tilgjengelighet?.numDocksAvailable ?: 0,
        tilgjengelighet?.let { "Ok" }
            ?: "Problem in retrieving data"

    )

    private fun epochToLocalDateTime(tilgjengelighet: Tilgjengelighet) =
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(tilgjengelighet.lastReported),
            ZoneOffset.UTC
        )
}