package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import kotlinx.coroutines.flow.Flow

interface CurrentDeviceTypeRepository {
    val currentDeviceTypeFlow: Flow<DeviceType>

    suspend fun saveCurrentDeviceType(deviceType: DeviceType)
    suspend fun clearCurrentDeviceType()
}