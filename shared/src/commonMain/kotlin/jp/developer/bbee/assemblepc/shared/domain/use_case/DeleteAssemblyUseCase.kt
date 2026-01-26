package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.first

class DeleteAssemblyUseCase(
    private val deviceRepository: DeviceRepository,
    private val updateCurrentCompositionUseCase: UpdateCurrentCompositionUseCase,
) {
    suspend operator fun invoke(
        assemblyId: Int,
        deviceId: String,
        quantity: Int,
    ) {
        val assemblies = deviceRepository.loadAssembly(assemblyId).first()
            .filter { it.deviceId == deviceId }
            .take(quantity)
        if (assemblies.isEmpty()) return

        deviceRepository.deleteAssemblies(assemblies)

        updateCurrentCompositionUseCase(assemblyId)
    }
}
