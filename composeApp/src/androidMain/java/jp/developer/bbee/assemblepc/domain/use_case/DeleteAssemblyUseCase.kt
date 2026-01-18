package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class DeleteAssemblyUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val updateCurrentCompositionUseCase: UpdateCurrentCompositionUseCase,
) {
    suspend operator fun invoke(
        assemblyId: Int,
        deviceId: String,
        quantity: Int
    ) {
        val assemblies = deviceRepository.loadAssembly(assemblyId)
            .filter { it.deviceId == deviceId }
            .take(quantity)
        if (assemblies.isEmpty()) return

        deviceRepository.deleteAssemblies(assemblies)

        updateCurrentCompositionUseCase(assemblyId)
    }
}
