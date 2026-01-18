package jp.developer.bbee.assemblepc.data.room.model.converter

import jp.developer.bbee.assemblepc.data.room.model.Device as DataDevice
import jp.developer.bbee.assemblepc.domain.model.Device
import java.time.LocalDateTime

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
        createdDate = LocalDateTime.parse(createddate),
        lastUpdate = LocalDateTime.parse(lastupdate),
    )

    fun List<DataDevice>.toDomain(): List<Device> = map { it.toDomain() }
}
