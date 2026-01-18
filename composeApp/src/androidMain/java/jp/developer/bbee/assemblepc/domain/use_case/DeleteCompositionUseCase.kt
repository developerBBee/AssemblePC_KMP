package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class DeleteCompositionUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val updateCurrentCompositionUseCase: UpdateCurrentCompositionUseCase,
) {

    suspend operator fun invoke(assemblyId: Int) {
        deviceRepository.deleteAssemblyById(assemblyId)

        updateCurrentCompositionUseCase(assemblyId, deleteIfEmpty = true)
    }
}
