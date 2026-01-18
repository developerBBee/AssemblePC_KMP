package jp.developer.bbee.assemblepc.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.developer.bbee.assemblepc.data.room.model.Assembly
import jp.developer.bbee.assemblepc.data.room.model.Device
import jp.developer.bbee.assemblepc.data.room.model.DeviceUpdate

/*
 * 注: Room 自動移行は、旧バージョンと新バージョンの両方のデータベースについて生成されたデータベース
 * スキーマに依存しています。exportSchema が false に設定されている場合、
 * または、データベースがまだ新しいバージョン番号でコンパイルされていない場合、自動移行は失敗します。
 */
@Database(
    entities = [Device::class, Assembly::class, DeviceUpdate::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAssemblyDeviceDao(): AssemblyDeviceDao
}
