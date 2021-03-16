package no.navneet.sanntidssykkeldata.api.external.Stasjoner

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OsloByStasjoner(
    val data: Data,
    val lastUpdated: Long,
    val ttl: Int
)