package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import javax.inject.Inject

class ClearCurrentCompositionUseCase @Inject constructor(
    private val repository: CurrentCompositionRepository,
) {

    suspend operator fun invoke() {
        repository.clearCurrentComposition()
    }
}
