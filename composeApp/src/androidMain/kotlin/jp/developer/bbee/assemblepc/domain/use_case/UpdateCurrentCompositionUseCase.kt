package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateCurrentCompositionUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val currentRepository: CurrentCompositionRepository
) {

    suspend operator fun invoke(assemblyId: Int, deleteIfEmpty: Boolean = false) {
        val composition = currentRepository.currentCompositionFlow.first()

        if (composition?.assemblyId == assemblyId) {
            val allAssemblies = deviceRepository.loadAssembly(assemblyId)

            if (allAssemblies.isEmpty()) {
                if (deleteIfEmpty) {
                    currentRepository.clearCurrentComposition()
                } else {
                    currentRepository.saveCurrentComposition(
                        composition.copy(items = emptyList())
                    )
                }
            } else {
                val deviceIdList = allAssemblies.map { it.deviceId }
                val devices = deviceRepository.loadDeviceByIds(deviceIdList)
                val firstAssembly = allAssemblies.first()

                currentRepository.saveCurrentComposition(
                    Composition.of(
                        assemblyId = assemblyId,
                        assemblyName = firstAssembly.assemblyName,
                        reviewText = firstAssembly.reviewText,
                        reviewTime = firstAssembly.reviewTime,
                        assemblies = allAssemblies,
                        devices = devices
                    )
                )
            }
        }
    }
}