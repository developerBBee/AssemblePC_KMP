package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow

class GetMaxAssemblyIdUseCase(
    private val repository: DeviceRepository,
) {
    operator fun invoke(): Flow<Int?> = repository.loadMaxAssemblyId()
}
