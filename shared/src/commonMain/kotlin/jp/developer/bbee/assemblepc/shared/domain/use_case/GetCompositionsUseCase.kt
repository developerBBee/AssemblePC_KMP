package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository

class GetCompositionsUseCase(
    private val repository: DeviceRepository,
){
    suspend operator fun invoke(): List<Composition> =
        repository.loadCompositions()
}
