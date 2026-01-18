package jp.developer.bbee.assemblepc.domain.model

import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.model.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * 構成情報
 */
@Serializable
data class Composition(
    val assemblyId: Int,
    val assemblyName: String,
    val items: List<CompositionItem>,
    val reviewText: String? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val reviewTime: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime,
) {

    fun getItem(deviceType: DeviceType): CompositionItem? =
        items.firstOrNull { it.deviceType == deviceType }

    fun updateReview(reviewText: String): Composition =
        copy(
            reviewText = reviewText,
            reviewTime = LocalDateTime.now(),
        )

    fun isReviewExpired(current: LocalDateTime): Boolean = if (reviewTime == null) {
        true
    } else {
        // レビューから１週間経過、または構成に変更があった場合、再レビュー可能
        current > reviewTime.plusWeeks(1) || updatedAt > reviewTime
    }

    companion object {
        fun of(
            assemblyId: Int,
            assemblyName: String,
            reviewText: String?,
            reviewTime: LocalDateTime?,
            assemblies: List<Assembly>,
            devices: List<Device>
        ): Composition {
            val items = assemblies.toCompositionItems(
                assemblyId = assemblyId,
                devices = devices
            )

            return Composition(
                assemblyId = assemblyId,
                assemblyName = assemblyName,
                items = items,
                reviewText = reviewText,
                reviewTime = reviewTime,
                updatedAt = assemblies.maxOf { it.updatedAt },
            )
        }
    }
}

@Serializable
data class CompositionItem(
    val quantity: Int,
    val deviceId: String,
    val deviceType: DeviceType,
    val deviceName : String,
    val deviceImgUrl: String,
    val deviceDetail: String,
    val devicePriceSaved: Price,
    val devicePriceRecent: Price,
    val url: String,
    val price: Price,
    val rank: Int,
    val releaseDate: String,
    val invisible: Boolean,
    val flag1: Int?,
    val flag2: Int?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdDate: LocalDateTime?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val lastUpdate: LocalDateTime?,
) {

    fun toDevice(): Device {
        return Device(
            id = deviceId,
            device = deviceType.key,
            name = deviceName,
            imgUrl = deviceImgUrl,
            url = url,
            detail = deviceDetail,
            price = price,
            rank = rank,
            releaseDate = releaseDate,
            invisible = invisible,
            flag1 = flag1,
            flag2 = flag2,
            createdDate = createdDate,
            lastUpdate = lastUpdate,
        )
    }

    companion object {
        fun of(
            quantity: Int,
            device: Device
        ): CompositionItem {
            return CompositionItem(
                quantity = quantity,
                deviceId = device.id,
                deviceType = DeviceType.from(device.device),
                deviceName = device.name,
                deviceImgUrl = device.imgUrl,
                deviceDetail = device.detail,
                devicePriceSaved = device.price,
                devicePriceRecent = device.price,
                url = device.url,
                price = device.price,
                rank = device.rank,
                releaseDate = device.releaseDate,
                invisible = device.invisible,
                flag1 = device.flag1,
                flag2 = device.flag2,
                createdDate = device.createdDate,
                lastUpdate = device.lastUpdate,
            )
        }
    }
}
