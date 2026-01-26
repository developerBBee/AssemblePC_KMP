package jp.developer.bbee.assemblepc.shared.domain.model

import jp.developer.bbee.assemblepc.shared.common.now
import kotlin.time.Instant

data class AssemblyReview(
    val assemblyId: Int,
    val reviewText: String,
    val reviewTime: Instant = now(),
)
