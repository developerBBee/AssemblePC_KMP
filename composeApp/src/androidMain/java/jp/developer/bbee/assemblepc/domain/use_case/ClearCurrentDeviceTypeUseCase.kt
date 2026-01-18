package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.CurrentDeviceTypeRepository
import javax.inject.Inject

class ClearCurrentDeviceTypeUseCase @Inject constructor(
    private val repository: CurrentDeviceTypeRepository,
) {

    suspend operator fun invoke() {
        repository.clearCurrentDeviceType()
    }
}
