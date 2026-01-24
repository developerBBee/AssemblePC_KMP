package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentDeviceTypeRepository

class ClearCurrentDeviceTypeUseCase(
    private val repository: CurrentDeviceTypeRepository,
) {

    suspend operator fun invoke() {
        repository.clearCurrentDeviceType()
    }
}
