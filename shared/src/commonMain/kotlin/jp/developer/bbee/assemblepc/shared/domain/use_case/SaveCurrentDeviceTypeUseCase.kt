package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentDeviceTypeRepository

class SaveCurrentDeviceTypeUseCase(
    private val repositoryImpl: CurrentDeviceTypeRepository,
) {

    suspend operator fun invoke(deviceType: DeviceType) {
        repositoryImpl.saveCurrentDeviceType(deviceType)
    }
}
