package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.Assembly
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository

class GetAssemblyUseCase(
    private val repository: DeviceRepository,
){
    suspend operator fun invoke(assemblyId: Int): List<Assembly> =
        repository.loadAssembly(assemblyId)
}
