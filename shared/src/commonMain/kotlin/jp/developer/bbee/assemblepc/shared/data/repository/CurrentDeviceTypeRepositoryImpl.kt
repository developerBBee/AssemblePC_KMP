package jp.developer.bbee.assemblepc.shared.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.domain.repository.CurrentDeviceTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CurrentDeviceTypeRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : CurrentDeviceTypeRepository {

    override val currentDeviceTypeFlow: Flow<DeviceType> = dataStore.data
        .map { prefs ->
            prefs[KEY_CURRENT_DEVICE_TYPE]?.let { DeviceType.from(it) } ?: DeviceType.PC_CASE
        }
        .catch { _ ->
            clearCurrentDeviceType()
            emit(DeviceType.PC_CASE)
        }

    override suspend fun saveCurrentDeviceType(deviceType: DeviceType) {
        dataStore.edit { prefs ->
            prefs[KEY_CURRENT_DEVICE_TYPE] = deviceType.key
        }
    }

    override suspend fun clearCurrentDeviceType() {
        dataStore.edit { prefs ->
            prefs.remove(KEY_CURRENT_DEVICE_TYPE)
        }
    }

    companion object {
        private val KEY_CURRENT_DEVICE_TYPE = stringPreferencesKey("current_device_type")
    }
}