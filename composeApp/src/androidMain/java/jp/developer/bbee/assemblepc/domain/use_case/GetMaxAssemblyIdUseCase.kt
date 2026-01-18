package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class GetMaxAssemblyIdUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    suspend operator fun invoke(): Int? = repository.loadMaxAssemblyId()
}