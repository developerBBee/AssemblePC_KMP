package jp.developer.bbee.assemblepc.shared.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

private const val DATA_STORE_DIR: String = "datastore"

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = {
        context.filesDir
            .resolve(DATA_STORE_DIR)
            .resolve(STORE_NAME)
            .absolutePath
    }
)
