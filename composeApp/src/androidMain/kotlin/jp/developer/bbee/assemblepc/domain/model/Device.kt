package jp.developer.bbee.assemblepc.domain.model

import java.time.LocalDateTime

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
    val createdDate: LocalDateTime?,
    val lastUpdate: LocalDateTime?
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