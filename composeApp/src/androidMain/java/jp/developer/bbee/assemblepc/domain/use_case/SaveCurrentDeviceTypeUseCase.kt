package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.repository.CurrentDeviceTypeRepository
import javax.inject.Inject

class SaveCurrentDeviceTypeUseCase @Inject constructor(
    private val repositoryImpl: CurrentDeviceTypeRepository
) {

    suspend operator fun invoke(deviceType: DeviceType) {
        repositoryImpl.saveCurrentDeviceType(deviceType)
    }
}