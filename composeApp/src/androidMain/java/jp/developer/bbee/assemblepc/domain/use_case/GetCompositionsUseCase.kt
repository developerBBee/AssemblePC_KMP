package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class GetCompositionsUseCase @Inject constructor(
    private val repository: DeviceRepository
){
    suspend operator fun invoke(): List<Composition> =
        repository.loadCompositions()
}