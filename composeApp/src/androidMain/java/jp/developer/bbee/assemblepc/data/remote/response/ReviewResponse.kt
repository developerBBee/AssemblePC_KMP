package jp.developer.bbee.assemblepc.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    val successData: ReviewSuccess?,
    val failureData: ReviewFailure?,
) {

    val isSuccess: Boolean
        get() = successData != null

    fun requireSuccess(): ReviewSuccess = checkNotNull(successData) {
        failureData?.errorMessage ?: "取得に失敗しました。"
    }
}

@Serializable
data class ReviewSuccess(
    val reviewText: String,
)

@Serializable
data class ReviewFailure(
    val errorMessage: String,
)
