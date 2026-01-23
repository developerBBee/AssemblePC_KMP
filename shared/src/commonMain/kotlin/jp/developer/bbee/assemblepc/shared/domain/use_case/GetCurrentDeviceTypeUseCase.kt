package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentDeviceTypeRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentDeviceTypeUseCase(
    private val repository: CurrentDeviceTypeRepository,
){
    operator fun invoke(): Flow<DeviceType> = repository.currentDeviceTypeFlow
}