package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
) {

    suspend operator fun invoke(composition: Composition): Composition {
        return reviewRepository.review(composition)
    }
}