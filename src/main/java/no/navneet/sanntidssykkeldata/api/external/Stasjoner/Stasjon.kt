package no.navneet.sanntidssykkeldata.api.external.Stasjoner

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Stasjon(
    val address: String,
    val capacity: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val stationId: String
)
