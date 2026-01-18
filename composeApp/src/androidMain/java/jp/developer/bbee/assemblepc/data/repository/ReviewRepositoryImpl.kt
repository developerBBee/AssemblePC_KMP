package jp.developer.bbee.assemblepc.data.repository

import jp.developer.bbee.assemblepc.data.remote.AssemblePcApi
import jp.developer.bbee.assemblepc.data.remote.request.ReviewRequest
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
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
