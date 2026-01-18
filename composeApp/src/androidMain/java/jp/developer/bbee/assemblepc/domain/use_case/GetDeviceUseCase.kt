package jp.developer.bbee.assemblepc.domain.use_case

import jp.developer.bbee.assemblepc.common.AppResponse
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {

    operator fun invoke(deviceType: DeviceType): Flow<AppResponse<List<Device>>> = flow {
        try {
            emit(AppResponse.Loading())
            val result = deviceRepository.getDeviceList(deviceType)
            emit(AppResponse.Success(result))
        } catch (e: java.lang.Exception) {
            emit(AppResponse.Failure(e.message.toString()))
        }
    }
}