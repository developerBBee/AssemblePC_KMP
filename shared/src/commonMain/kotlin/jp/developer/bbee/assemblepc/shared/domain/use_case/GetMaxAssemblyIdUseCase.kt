package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository

class GetMaxAssemblyIdUseCase(
    private val repository: DeviceRepository,
) {
    suspend operator fun invoke(): Int? = repository.loadMaxAssemblyId()
}
