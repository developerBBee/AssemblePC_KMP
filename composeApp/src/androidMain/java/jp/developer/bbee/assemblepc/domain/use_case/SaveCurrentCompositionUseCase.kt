package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import javax.inject.Inject

class SaveCurrentCompositionUseCase @Inject constructor(
    private val repository: CurrentCompositionRepository,
){
    suspend operator fun invoke(composition: Composition) {
        repository.saveCurrentComposition(composition)
    }
}