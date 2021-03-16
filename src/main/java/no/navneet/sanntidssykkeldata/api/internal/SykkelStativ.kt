package no.navneet.sanntidssykkeldata.api.internal

import java.time.LocalDateTime

data class SykkelStativ(
    val stationId:String,
    val updatedAt: LocalDateTime,
    val name: String,
    val address: String,
    val capacity: Int,
    val numBikeAvailable:Int,
    val numLockAvailable:Int,
    val message:String?
)