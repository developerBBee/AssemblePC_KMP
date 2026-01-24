package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentCompositionRepository

class SaveCurrentCompositionUseCase(
    private val repository: CurrentCompositionRepository,
){
    suspend operator fun invoke(composition: Composition) {
        repository.saveCurrentComposition(composition)
    }
}
