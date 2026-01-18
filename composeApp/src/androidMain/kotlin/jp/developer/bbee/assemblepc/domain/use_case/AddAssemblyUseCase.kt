package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class AddAssemblyUseCase @Inject constructor(
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