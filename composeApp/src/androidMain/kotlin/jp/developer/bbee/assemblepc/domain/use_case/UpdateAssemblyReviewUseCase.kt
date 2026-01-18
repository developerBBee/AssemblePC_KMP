package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.AssemblyReview
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import javax.inject.Inject

class UpdateAssemblyReviewUseCase @Inject constructor(
    private val repository: DeviceRepository,
) {

    suspend operator fun invoke(assemblyReview: AssemblyReview) {
        repository.updateAssemblyReview(assemblyReview)
    }
}