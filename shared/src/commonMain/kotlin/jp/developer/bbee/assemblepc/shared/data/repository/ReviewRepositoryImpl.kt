package jp.developer.bbee.assemblepc.shared.data.repository

import jp.developer.bbee.assemblepc.shared.data.remote.AssemblePcApi
import jp.developer.bbee.assemblepc.shared.data.remote.request.ReviewRequest
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.repository.ReviewRepository

class ReviewRepositoryImpl(
    private val assemblyPcApi: AssemblePcApi,
) : ReviewRepository {

    override suspend fun review(composition: Composition): Composition {
        val request = ReviewRequest.from(composition)
        val response = assemblyPcApi.review(request)
        val success = if (response.isSuccess) {
            response.requireSuccess()
        } else {
            throw IllegalArgumentException(response.failureData?.errorMessage)
        }
        return composition.updateReview(success.reviewText)
    }
}
