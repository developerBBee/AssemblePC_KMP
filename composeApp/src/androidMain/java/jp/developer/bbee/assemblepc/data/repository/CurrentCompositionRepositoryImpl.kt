package jp.developer.bbee.assemblepc.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.developer.bbee.assemblepc.common.defaultJson
import jp.developer.bbee.assemblepc.data.store.dataStore
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrentCompositionRepositoryImpl @Inject constructor(
    private val context: Context,
) : CurrentCompositionRepository {

    override val currentCompositionFlow = getCurrentComposition()

    override suspend fun saveCurrentComposition(composition: Composition) {
        context.dataStore.edit { prefs ->
            prefs[CURRENT_COMPOSITION_KEY] = defaultJson.encodeToString(composition)
        }
    }

    override suspend fun clearCurrentComposition() {
        context.dataStore.edit { prefs ->
            prefs.remove(CURRENT_COMPOSITION_KEY)
        }
    }

    private fun getCurrentComposition(): Flow<Composition?> =
        context.dataStore.data
            .map { prefs ->
                prefs[CURRENT_COMPOSITION_KEY]
                    ?.let { defaultJson.decodeFromString<Composition>(it) }
            }
            .catch { _ ->
                clearCurrentComposition()
                emit(null)
            }

    companion object {
        private val CURRENT_COMPOSITION_KEY = stringPreferencesKey("current_composition")
    }
}
