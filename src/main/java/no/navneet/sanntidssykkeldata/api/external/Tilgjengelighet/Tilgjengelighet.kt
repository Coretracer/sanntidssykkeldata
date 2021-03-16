package no.navneet.sanntidssykkeldata.api.external.Tilgjengelighet

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Tilgjengelighet(
    val isInstalled: Int,
    val isRenting: Int,
    val isReturning: Int,
    val lastReported: Long,
    val numBikesAvailable: Int,
    val numDocksAvailable: Int,
    val stationId: String
)