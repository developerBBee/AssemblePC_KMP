package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentCompositionRepository

class ClearCurrentCompositionUseCase(
    private val repository: CurrentCompositionRepository,
) {

    suspend operator fun invoke() {
        repository.clearCurrentComposition()
    }
}
