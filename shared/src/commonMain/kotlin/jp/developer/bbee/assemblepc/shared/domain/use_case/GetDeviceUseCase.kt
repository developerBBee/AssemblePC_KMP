package jp.developer.bbee.assemblepc.shared.domain.use_case

import jp.developer.bbee.assemblepc.shared.common.AppResponse
import jp.developer.bbee.assemblepc.shared.domain.model.Device
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDeviceUseCase(private val deviceRepository: DeviceRepository) {

    operator fun invoke(deviceType: DeviceType): Flow<AppResponse<List<Device>>> = flow {
        try {
            emit(AppResponse.Loading())
            val result = deviceRepository.getDeviceList(deviceType)
            emit(AppResponse.Success(result))
        } catch (e: Exception) {
            emit(AppResponse.Failure(e.message.toString()))
        }
    }
}
