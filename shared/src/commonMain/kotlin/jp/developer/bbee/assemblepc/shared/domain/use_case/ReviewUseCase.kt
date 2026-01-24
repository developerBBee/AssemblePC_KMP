package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.repository.ReviewRepository

class ReviewUseCase(
    private val reviewRepository: ReviewRepository,
) {

    suspend operator fun invoke(composition: Composition): Composition {
        return reviewRepository.review(composition)
    }
}
