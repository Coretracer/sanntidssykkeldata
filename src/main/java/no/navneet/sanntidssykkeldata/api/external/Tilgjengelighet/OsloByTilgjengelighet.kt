package no.navneet.sanntidssykkeldata.api.external.Tilgjengelighet

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OsloByTilgjengelighet(
    val data: Data,
    val lastUpdated: Int,
    val ttl: Int
)
