package no.navneet.sanntidssykkeldata.api

data class ResponseWrapper<T>(
    val lateUpdated: Long,
    val data: List<T>
)
