package jp.developer.bbee.assemblepc.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jakarta.inject.Inject
import jp.developer.bbee.assemblepc.data.store.dataStore
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.repository.CurrentDeviceTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CurrentDeviceTypeRepositoryImpl @Inject constructor(
    private val context: Context,
) : CurrentDeviceTypeRepository {

    override val currentDeviceTypeFlow: Flow<DeviceType> = context.dataStore.data
        .map { prefs ->
            prefs[KEY_CURRENT_DEVICE_TYPE]?.let { DeviceType.from(it) } ?: DeviceType.PC_CASE
        }
        .catch { _ ->
            clearCurrentDeviceType()
            emit(DeviceType.PC_CASE)
        }

    override suspend fun saveCurrentDeviceType(deviceType: DeviceType) {
        context.dataStore.edit { prefs ->
            prefs[KEY_CURRENT_DEVICE_TYPE] = deviceType.key
        }
    }

    override suspend fun clearCurrentDeviceType() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_CURRENT_DEVICE_TYPE)
        }
    }

    companion object {
        private val KEY_CURRENT_DEVICE_TYPE = stringPreferencesKey("current_device_type")
    }
}