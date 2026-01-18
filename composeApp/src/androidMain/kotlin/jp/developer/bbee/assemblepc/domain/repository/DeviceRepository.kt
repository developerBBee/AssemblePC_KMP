package jp.developer.bbee.assemblepc.domain.repository

import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.AssemblyReview
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.DeviceUpdate
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType

interface DeviceRepository {
    suspend fun getDeviceList(deviceType: DeviceType): List<Device>

    suspend fun loadAssembly(assemblyId: Int): List<Assembly>
    suspend fun loadCompositions(): List<Composition>
    suspend fun insertAssemblies(assemblies: List<Assembly>)
    suspend fun loadMaxAssemblyId(): Int?
    suspend fun deleteAssemblies(assemblies: List<Assembly>)
    suspend fun deleteAssemblyById(assemblyId: Int)
    suspend fun renameAssemblyById(assemblyName: String, assemblyId: Int)
    suspend fun updateAssemblyReview(assemblyReview: AssemblyReview)

    suspend fun existDeviceUpdate(device: String): Int
    suspend fun loadDeviceUpdate(device: String): List<DeviceUpdate>
    suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate)

    suspend fun loadDevice(device: String): List<Device>
    suspend fun loadDeviceByIds(deviceIds: List<String>): List<Device>
    suspend fun insertDevice(device: Device)
}