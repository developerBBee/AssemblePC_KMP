package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.Assembly
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository

class AddAssemblyUseCase(
    private val deviceRepository: DeviceRepository,
    private val updateCurrentCompositionUseCase: UpdateCurrentCompositionUseCase,
) {
    suspend operator fun invoke(assemblies: List<Assembly>) {
        if (assemblies.isEmpty()) return

        deviceRepository.insertAssemblies(assemblies)

        val assemblyId = assemblies.first().assemblyId
        updateCurrentCompositionUseCase(assemblyId)
    }
}
