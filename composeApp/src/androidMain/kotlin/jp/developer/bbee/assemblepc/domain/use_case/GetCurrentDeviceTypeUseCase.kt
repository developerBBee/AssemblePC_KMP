package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.repository.CurrentDeviceTypeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentDeviceTypeUseCase @Inject constructor(
    private val repository: CurrentDeviceTypeRepository,
){
    operator fun invoke(): Flow<DeviceType> = repository.currentDeviceTypeFlow
}