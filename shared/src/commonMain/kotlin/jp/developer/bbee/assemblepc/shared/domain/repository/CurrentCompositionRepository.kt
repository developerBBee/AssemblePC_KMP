package jp.developer.bbee.assemblepc.shared.domain.repository

import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import kotlinx.coroutines.flow.Flow

interface CurrentCompositionRepository {
    val currentCompositionFlow: Flow<Composition?>

    suspend fun saveCurrentComposition(composition: Composition)
    suspend fun clearCurrentComposition()
}