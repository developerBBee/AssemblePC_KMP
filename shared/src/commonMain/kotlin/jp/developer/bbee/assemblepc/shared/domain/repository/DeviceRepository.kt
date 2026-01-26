package jp.developer.bbee.assemblepc.shared.domain.repository

import jp.developer.bbee.assemblepc.shared.domain.model.Assembly
import jp.developer.bbee.assemblepc.shared.domain.model.AssemblyReview
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.model.Device
import jp.developer.bbee.assemblepc.shared.domain.model.DeviceUpdate
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    suspend fun getDeviceList(deviceType: DeviceType): List<Device>

    fun loadAssembly(assemblyId: Int): Flow<List<Assembly>>
    fun loadCompositions(): Flow<List<Composition>>
    suspend fun insertAssemblies(assemblies: List<Assembly>)
    fun loadMaxAssemblyId(): Flow<Int?>
    suspend fun deleteAssemblies(assemblies: List<Assembly>)
    suspend fun deleteAssemblyById(assemblyId: Int)
    suspend fun renameAssemblyById(assemblyName: String, assemblyId: Int)
    suspend fun updateAssemblyReview(assemblyReview: AssemblyReview)

    suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate)

    fun loadDeviceByIds(deviceIds: List<String>): Flow<List<Device>>
    suspend fun insertDevice(device: Device)
}