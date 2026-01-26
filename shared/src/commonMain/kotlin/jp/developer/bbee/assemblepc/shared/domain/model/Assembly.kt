package jp.developer.bbee.assemblepc.shared.domain.model

import jp.developer.bbee.assemblepc.shared.common.now
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.domain.model.serializer.InstantTokyoSerializer
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Assembly(
    val id: Int = 0,
    val assemblyId: Int,
    val assemblyName: String,
    val deviceId: String,
    val deviceType: DeviceType,
    val deviceName : String,
    val deviceImgUrl: String,
    val deviceDetail: String,
    val devicePriceSaved: Price,
    val devicePriceRecent: Price,
    val reviewText: String? = null,
    @Serializable(with = InstantTokyoSerializer::class)
    val reviewTime: Instant? = null,
    @Serializable(with = InstantTokyoSerializer::class)
    val updatedAt: Instant = now(),
)

fun List<Assembly>.toCompositionItems(
    assemblyId: Int,
    devices: List<Device>
): List<CompositionItem> =
    filter { it.assemblyId == assemblyId }
        .groupingBy { it.deviceId }
        .eachCount()
        .mapNotNull { (id, quantity) ->
            devices.find { it.id == id }
                ?.let { device -> device to quantity }
        }
        .map { (device, quantity) ->
            CompositionItem.of(
                quantity = quantity,
                device = device
            )
        }
