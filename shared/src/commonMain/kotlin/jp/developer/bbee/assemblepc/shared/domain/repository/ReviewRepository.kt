package jp.developer.bbee.assemblepc.shared.domain.repository

import jp.developer.bbee.assemblepc.shared.domain.model.Composition

interface ReviewRepository {
    suspend fun review(composition: Composition): Composition
}
