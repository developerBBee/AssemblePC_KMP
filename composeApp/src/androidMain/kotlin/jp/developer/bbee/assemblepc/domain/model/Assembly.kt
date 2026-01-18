package jp.developer.bbee.assemblepc.domain.model

import jp.developer.bbee.assemblepc.domain.model.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Assembly(
    val id: Int = 0,
    val assemblyId: Int,
    val assemblyName: String,
    val deviceId: String,
    val deviceType: String,
    val deviceName : String,
    val deviceImgUrl: String,
    val deviceDetail: String,
    val devicePriceSaved: Price,
    val devicePriceRecent: Price,
    val reviewText: String? = null,
    @Serializable(LocalDateTimeSerializer::class)
    val reviewTime: LocalDateTime? = null,
    @Serializable(LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
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
