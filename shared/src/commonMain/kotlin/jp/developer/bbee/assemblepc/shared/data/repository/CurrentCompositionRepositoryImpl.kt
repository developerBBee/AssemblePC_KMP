package jp.developer.bbee.assemblepc.shared.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.developer.bbee.assemblepc.shared.common.defaultJson
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentCompositionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CurrentCompositionRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : CurrentCompositionRepository {

    override val currentCompositionFlow = getCurrentComposition()

    override suspend fun saveCurrentComposition(composition: Composition) {
        dataStore.edit { prefs ->
            prefs[CURRENT_COMPOSITION_KEY] = defaultJson.encodeToString(composition)
        }
    }

    override suspend fun clearCurrentComposition() {
        dataStore.edit { prefs ->
            prefs.remove(CURRENT_COMPOSITION_KEY)
        }
    }

    private fun getCurrentComposition(): Flow<Composition?> =
        dataStore.data
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
