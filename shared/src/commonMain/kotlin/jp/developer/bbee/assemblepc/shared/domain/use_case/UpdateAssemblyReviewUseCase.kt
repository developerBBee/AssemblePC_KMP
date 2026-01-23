package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.AssemblyReview
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository

class UpdateAssemblyReviewUseCase(
    private val repository: DeviceRepository,
) {

    suspend operator fun invoke(assemblyReview: AssemblyReview) {
        repository.updateAssemblyReview(assemblyReview)
    }
}
