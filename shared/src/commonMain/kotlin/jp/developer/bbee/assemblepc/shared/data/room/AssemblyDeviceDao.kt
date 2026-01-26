package jp.developer.bbee.assemblepc.shared.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.developer.bbee.assemblepc.shared.data.room.model.Assembly
import jp.developer.bbee.assemblepc.shared.data.room.model.Device
import jp.developer.bbee.assemblepc.shared.data.room.model.DeviceUpdate
import kotlinx.coroutines.flow.Flow

@Dao
interface AssemblyDeviceDao {

    /**
     * Device Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: Device)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevices(devices: List<Device>)

    @Query("SELECT * FROM Device WHERE device = :device")
    fun loadDevice(device: String): Flow<List<Device>>

    @Query("SELECT * FROM Device WHERE id IN (:ids)")
    fun loadDeviceByIds(ids: List<String>): Flow<List<Device>>

    /**
     * Assembly Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssemblies(assemblies: List<Assembly>)

    @Query("SELECT * FROM Assembly WHERE assemblyId = :assemblyId")
    fun loadAssembly(assemblyId: Int): Flow<List<Assembly>>

    @Query("SELECT * FROM Assembly ORDER BY assemblyId DESC")
    fun loadAllAssembly(): Flow<List<Assembly>>

    @Delete
    suspend fun deleteAssembly(assemblies: List<Assembly>)

    @Query("DELETE FROM Assembly WHERE assemblyId = :assemblyId")
    suspend fun deleteAssemblyById(assemblyId: Int)

    @Query("UPDATE Assembly SET assemblyName = :assemblyName WHERE assemblyId = :assemblyId")
    suspend fun renameAssemblyById(assemblyName: String, assemblyId: Int)

    @Query("SELECT MAX(assemblyId) FROM Assembly")
    fun loadMaxAssemblyId(): Flow<Int?>

    @Query("UPDATE Assembly SET reviewText = :reviewText, reviewTime = :reviewTime" +
            " WHERE assemblyId = :assemblyId ")
    suspend fun updateAssemblyReview(assemblyId: Int, reviewText: String, reviewTime: String)

    /**
     * DeviceUpdate Table CRUD
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate)

    @Query("SELECT * FROM DeviceUpdate WHERE device = :device")
    fun loadDeviceUpdate(device: String): Flow<List<DeviceUpdate>>
}