package no.navneet.sanntidssykkeldata.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import no.navneet.sanntidssykkeldata.api.external.Stasjoner.Data
import no.navneet.sanntidssykkeldata.api.external.Stasjoner.OsloByStasjoner
import no.navneet.sanntidssykkeldata.client.SanntidsDataClient
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit

class StasjonerDataCache(private val sanntidsDataClient: SanntidsDataClient) {

    private val stasjonerDataCache: LoadingCache<String, OsloByStasjoner> = Caffeine.newBuilder()
        .refreshAfterWrite(REFRESH_TIME_IN_SECONDS, TimeUnit.SECONDS)
        .build { loadOsloByStasjoner() }

    fun getOsloByStasjoner(): OsloByStasjoner {
        return stasjonerDataCache.get(STASJONER_DATA_CACHE_NAME) ?: OsloByStasjoner(
            Data(emptyList()),
            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            DEFAULT_TTL
        )
    }

    private fun loadOsloByStasjoner(): OsloByStasjoner? {
        return sanntidsDataClient.getStasjonerDataViaApi()
    }

    companion object {
        const val STASJONER_DATA_CACHE_NAME = "stasjonerDataCacheName"
        const val DEFAULT_TTL = 10
        const val REFRESH_TIME_IN_SECONDS = 300L
    }

}