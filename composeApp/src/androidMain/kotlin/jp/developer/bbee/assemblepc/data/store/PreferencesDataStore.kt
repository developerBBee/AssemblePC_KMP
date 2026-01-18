package jp.developer.bbee.assemblepc.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val STORE_NAME = "assemble_pc_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)
