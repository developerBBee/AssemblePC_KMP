package jp.developer.bbee.assemblepc.domain.model

import java.time.LocalDateTime

data class AssemblyReview(
    val assemblyId: Int,
    val reviewText: String,
    val reviewTime: LocalDateTime,
)
