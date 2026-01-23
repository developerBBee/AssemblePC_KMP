package jp.developer.bbee.assemblepc.shared.domain.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Device(
    val id: String,
    val device: String,
    val name: String,
    val imgUrl: String,
    val url: String,
    val detail: String,
    val price: Price,
    val rank: Int,
    val releaseDate: String,
    val invisible: Boolean,
    val flag1: Int?,
    val flag2: Int?,
    val createdDate: Instant?,
    val lastUpdate: Instant?
) {

    fun toAssembly(
        assemblyId: Int,
        assemblyName: String
    ): Assembly {
        return Assembly(
            assemblyId = assemblyId,
            assemblyName = assemblyName,
            deviceId = id,
            deviceType = device,
            deviceName = name,
            deviceImgUrl = imgUrl,
            deviceDetail = detail,
            devicePriceSaved = price,
            devicePriceRecent = price
        )
    }
}