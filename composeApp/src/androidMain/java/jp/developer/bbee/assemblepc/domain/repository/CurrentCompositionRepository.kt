package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.domain.model.Composition
import kotlinx.coroutines.flow.Flow

interface CurrentCompositionRepository {
    val currentCompositionFlow: Flow<Composition?>

    suspend fun saveCurrentComposition(composition: Composition)
    suspend fun clearCurrentComposition()
}