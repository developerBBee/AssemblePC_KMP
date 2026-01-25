@file:OptIn(ExperimentalTime::class)

package jp.developer.bbee.assemblepc.shared.data.room.model.converter

import jp.developer.bbee.assemblepc.shared.common.toTokyoInstant
import jp.developer.bbee.assemblepc.shared.data.room.model.Assembly as DataAssembly
import jp.developer.bbee.assemblepc.shared.domain.model.Assembly
import kotlin.time.ExperimentalTime

object AssemblyConverter {
    fun Assembly.toData(): DataAssembly = DataAssembly(
        id = id,
        assemblyId = assemblyId,
        assemblyName = assemblyName,
        deviceId = deviceId,
        deviceType = deviceType,
        deviceName = deviceName,
        deviceImgUrl = deviceImgUrl,
        deviceDetail = deviceDetail,
        devicePriceSaved = devicePriceSaved,
        devicePriceRecent = devicePriceRecent,
        reviewText = reviewText,
        reviewTime = reviewTime?.toString(),
        updatedAt = updatedAt.toString()
    )

    fun List<Assembly>.toData(): List<DataAssembly> = map { it.toData() }

    private fun DataAssembly.toDomain(): Assembly = Assembly(
        id = id,
        assemblyId = assemblyId,
        assemblyName = assemblyName,
        deviceId = deviceId,
        deviceType = deviceType,
        deviceName = deviceName,
        deviceImgUrl = deviceImgUrl,
        deviceDetail = deviceDetail,
        devicePriceSaved = devicePriceSaved,
        devicePriceRecent = devicePriceRecent,
        reviewText = reviewText,
        reviewTime = reviewTime?.toTokyoInstant(),
        updatedAt = updatedAt.toTokyoInstant()
    )

    fun List<DataAssembly>.toDomain(): List<Assembly> = map { it.toDomain() }
}
