package jp.developer.bbee.assemblepc.shared.data.room.model.converter

import jp.developer.bbee.assemblepc.shared.data.room.model.DeviceUpdate as DataDeviceUpdate
import jp.developer.bbee.assemblepc.shared.domain.model.DeviceUpdate

object DeviceUpdateConverter {
    fun DeviceUpdate.toData(): DataDeviceUpdate = DataDeviceUpdate(
        device = device,
        update = update,
    )

    fun List<DeviceUpdate>.toData(): List<DataDeviceUpdate> = map { it.toData() }

    private fun DataDeviceUpdate.toDomain(): DeviceUpdate = DeviceUpdate(
        device = device,
        update = update,
    )

    fun List<DataDeviceUpdate>.toDomain(): List<DeviceUpdate> = map { it.toDomain() }
}
