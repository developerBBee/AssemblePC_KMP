package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.first
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class RenameAssemblyUseCase(
    private val deviceRepository: DeviceRepository,
    private val currentRepository: CurrentCompositionRepository,
) {

    suspend operator fun invoke(assemblyName: String, assemblyId: Int) {
        deviceRepository.renameAssemblyById(assemblyName, assemblyId)
        val composition = currentRepository.currentCompositionFlow.first()
        if (composition?.assemblyId == assemblyId) {
            currentRepository.saveCurrentComposition(composition.copy(assemblyName = assemblyName))
        }
    }
}
