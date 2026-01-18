package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentCompositionUseCase @Inject constructor(
    private val repository: CurrentCompositionRepository,
){
    operator fun invoke(): Flow<Composition?> = repository.currentCompositionFlow
}