package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RenameAssemblyUseCase @Inject constructor(
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