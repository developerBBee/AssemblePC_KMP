package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository

class DeleteCompositionUseCase(
    private val deviceRepository: DeviceRepository,
    private val updateCurrentCompositionUseCase: UpdateCurrentCompositionUseCase,
) {

    suspend operator fun invoke(assemblyId: Int) {
        deviceRepository.deleteAssemblyById(assemblyId)

        updateCurrentCompositionUseCase(assemblyId, deleteIfEmpty = true)
    }
}
