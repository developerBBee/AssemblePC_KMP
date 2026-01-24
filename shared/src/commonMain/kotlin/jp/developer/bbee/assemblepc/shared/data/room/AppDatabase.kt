package jp.developer.bbee.assemblepc.shared.data.room

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import jp.developer.bbee.assemblepc.shared.data.room.model.Assembly
import jp.developer.bbee.assemblepc.shared.data.room.model.Device
import jp.developer.bbee.assemblepc.shared.data.room.model.DeviceUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal const val DB_NAME = "assemblepc_database"

@Database(
    entities = [Device::class, Assembly::class, DeviceUpdate::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAssemblyDeviceDao(): AssemblyDeviceDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
