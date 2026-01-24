@file:OptIn(ExperimentalTime::class)

package jp.developer.bbee.assemblepc.shared.data.room.model.converter

import jp.developer.bbee.assemblepc.shared.common.toTokyoInstant
import jp.developer.bbee.assemblepc.shared.data.room.model.Device as DataDevice
import jp.developer.bbee.assemblepc.shared.domain.model.Device
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object DeviceConverter {
    fun Device.toData(): DataDevice = DataDevice(
        id = id,
        device = device,
        name = name,
        imgurl = imgUrl,
        url = url,
        detail = detail,
        price = price,
        rank = rank,
        releasedate = releaseDate,
        invisible = invisible,
        flag1 = flag1,
        flag2 = flag2,
        createddate = createdDate?.toString(),
        lastupdate = lastUpdate?.toString(),
    )

    fun List<Device>.toData(): List<DataDevice> = map { it.toData() }

    private fun DataDevice.toDomain(): Device = Device(
        id = id,
        device = device,
        name = name,
        imgUrl = imgurl,
        url = url,
        detail = detail,
        price = price,
        rank = rank,
        releaseDate = releasedate,
        invisible = invisible,
        flag1 = flag1,
        flag2 = flag2,
        createdDate = createddate?.toTokyoInstant(),
        lastUpdate = lastupdate?.toTokyoInstant(),
    )

    fun List<DataDevice>.toDomain(): List<Device> = map { it.toDomain() }
}
