package jp.developer.bbee.assemblepc.shared.data.repository

import jp.developer.bbee.assemblepc.shared.data.remote.AssemblePcApi
import jp.developer.bbee.assemblepc.shared.data.remote.toIntUpdate
import jp.developer.bbee.assemblepc.shared.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.shared.data.room.model.converter.AssemblyConverter.toData
import jp.developer.bbee.assemblepc.shared.data.room.model.converter.AssemblyConverter.toDomain
import jp.developer.bbee.assemblepc.shared.data.room.model.converter.DeviceConverter.toData
import jp.developer.bbee.assemblepc.shared.data.room.model.converter.DeviceConverter.toDomain
import jp.developer.bbee.assemblepc.shared.data.room.model.converter.DeviceUpdateConverter.toData
import jp.developer.bbee.assemblepc.shared.domain.model.Assembly
import jp.developer.bbee.assemblepc.shared.domain.model.AssemblyReview
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.model.Device
import jp.developer.bbee.assemblepc.shared.domain.model.DeviceUpdate
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import jp.developer.bbee.assemblepc.shared.data.room.model.DeviceUpdate as DataDeviceUpdate

class DeviceRepositoryImpl(
    private val api: AssemblePcApi,
    private val assemblyDeviceDao: AssemblyDeviceDao
) : DeviceRepository {

    private var apiUpdate: Int = 0

    private val storedUpdate: MutableMap<DeviceType, Int> = mutableMapOf(
        DeviceType.PC_CASE to 0,
        DeviceType.MOTHER_BOARD to 0,
        DeviceType.POWER_SUPPLY to 0,
        DeviceType.CPU to 0,
        DeviceType.CPU_COOLER to 0,
        DeviceType.MEMORY to 0,
        DeviceType.SSD to 0,
        DeviceType.HDD to 0,
        DeviceType.VIDEO_CARD to 0,
        DeviceType.OS to 0,
        DeviceType.DISPLAY to 0,
        DeviceType.KEYBOARD to 0,
        DeviceType.MOUSE to 0,
        DeviceType.DVD_DRIVE to 0,
        DeviceType.BD_DRIVE to 0,
        DeviceType.SOUND_CARD to 0,
        DeviceType.SPEAKER to 0,
        DeviceType.FAN_CONTROLLER to 0,
        DeviceType.CASE_FAN to 0
    )

    override suspend fun getDeviceList(deviceType: DeviceType): List<Device> {
        if (apiUpdate == 0) {
            apiUpdate = api.getLastUpdate().toIntUpdate()
        }
        if (storedUpdate[deviceType] == 0) {
            val storedDeviceUpdates = assemblyDeviceDao.loadDeviceUpdate(deviceType.key).first()
            if (storedDeviceUpdates.isNotEmpty()) {
                storedUpdate[deviceType] = storedDeviceUpdates.first().update
            }
        }
        if ((storedUpdate[deviceType] ?: 0) < apiUpdate) {
            val deviceList = api.getDeviceList(deviceType.key).toDevice()
            assemblyDeviceDao.insertDevices(deviceList)
            val deviceUpdate = DataDeviceUpdate(deviceType.key, apiUpdate)
            assemblyDeviceDao.insertDeviceUpdate(deviceUpdate)
            return deviceList.toDomain()
        }
        return assemblyDeviceDao.loadDevice(deviceType.key).first().toDomain()
    }

    override fun loadAssembly(assemblyId: Int): Flow<List<Assembly>> {
        return assemblyDeviceDao.loadAssembly(assemblyId).map { it.toDomain() }
    }

    override fun loadCompositions(): Flow<List<Composition>> {
        return assemblyDeviceDao.loadAllAssembly()
            .map { assembliesData ->
                val assemblies = assembliesData.toDomain()
                val deviceIds = assemblies.map { it.deviceId }
                val devices = assemblyDeviceDao.loadDeviceByIds(deviceIds).first().toDomain()
                if (assemblies.isEmpty() || devices.isEmpty()) {
                    emptyList()
                } else {
                    assemblies
                        .groupBy { it.assemblyId }
                        .map { (assemblyId, assemblyList) ->
                            val firstAssembly = assemblyList.first()
                            Composition.of(
                                assemblyId = assemblyId,
                                assemblyName = firstAssembly.assemblyName,
                                reviewText = firstAssembly.reviewText,
                                reviewTime = firstAssembly.reviewTime,
                                assemblies = assemblyList,
                                devices = devices
                            )
                        }
                }
            }
    }

    override suspend fun insertAssemblies(assemblies: List<Assembly>) {
        assemblyDeviceDao.insertAssemblies(assemblies.toData())
    }

    override fun loadMaxAssemblyId(): Flow<Int?> {
        return assemblyDeviceDao.loadMaxAssemblyId()
    }

    override suspend fun deleteAssemblies(assemblies: List<Assembly>) {
        assemblyDeviceDao.deleteAssembly(assemblies.toData())
    }

    override suspend fun deleteAssemblyById(assemblyId: Int) {
        assemblyDeviceDao.deleteAssemblyById(assemblyId)
    }

    override suspend fun renameAssemblyById(assemblyName: String, assemblyId: Int) {
        assemblyDeviceDao.renameAssemblyById(assemblyName, assemblyId)
    }

    override suspend fun updateAssemblyReview(assemblyReview: AssemblyReview) {
        assemblyDeviceDao.updateAssemblyReview(
            assemblyId = assemblyReview.assemblyId,
            reviewText = assemblyReview.reviewText,
            reviewTime = assemblyReview.reviewTime.toString()
        )
    }

    override suspend fun insertDeviceUpdate(deviceUpdate: DeviceUpdate) {
        assemblyDeviceDao.insertDeviceUpdate(deviceUpdate.toData())
    }

    override fun loadDeviceByIds(deviceIds: List<String>): Flow<List<Device>> {
        return assemblyDeviceDao.loadDeviceByIds(deviceIds).map { it.toDomain() }
    }

    override suspend fun insertDevice(device: Device) {
        assemblyDeviceDao.insertDevice(device.toData())
    }
}