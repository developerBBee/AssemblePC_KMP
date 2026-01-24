package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentCompositionRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentCompositionUseCase(
    private val repository: CurrentCompositionRepository,
){
    operator fun invoke(): Flow<Composition?> = repository.currentCompositionFlow
}