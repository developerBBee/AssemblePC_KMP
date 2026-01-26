package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow

class GetCompositionsUseCase(
    private val repository: DeviceRepository,
){
    operator fun invoke(): Flow<List<Composition>> =
        repository.loadCompositions()
}
