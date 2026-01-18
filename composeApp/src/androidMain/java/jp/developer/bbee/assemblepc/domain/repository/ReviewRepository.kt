package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.domain.model.Composition

interface ReviewRepository {
    suspend fun review(composition: Composition): Composition
}
