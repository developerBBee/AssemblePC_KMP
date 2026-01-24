package jp.developer.bbee.assemblepc.shared.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import jp.developer.bbee.assemblepc.shared.data.room.AppDatabase
import jp.developer.bbee.assemblepc.shared.data.room.getDatabaseBuilder
import jp.developer.bbee.assemblepc.shared.data.room.getRoomDatabase
import jp.developer.bbee.assemblepc.shared.data.store.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module = module {
    single<DataStore<Preferences>> { createDataStore() }
    single<AppDatabase> { getRoomDatabase(getDatabaseBuilder()) }
}
