package jp.developer.bbee.assemblepc.presentation.screen.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.common.Constants.KANA_HALF_TO_FULL
import jp.developer.bbee.assemblepc.common.AppResponse
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.model.MAX_PRICE
import jp.developer.bbee.assemblepc.domain.model.ZERO_PRICE
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.use_case.AddAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.DeleteAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetCurrentDeviceTypeUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetDeviceUseCase
import jp.developer.bbee.assemblepc.presentation.screen.device.components.SortType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    getCurrentCompositionUseCase: GetCurrentCompositionUseCase,
    getCurrentDeviceTypeUseCase: GetCurrentDeviceTypeUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val addAssemblyUseCase: AddAssemblyUseCase,
    private val deleteAssemblyUseCase: DeleteAssemblyUseCase,
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, ex -> handleError(ex) }

    private val compositionFlow = MutableStateFlow<Composition?>(null)
    private val devicesFlow = MutableStateFlow<List<Device>>(emptyList())

    private val _uiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Loading)
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    private val _dialogUiState = MutableStateFlow<ShowDeviceAdditionDialog?>(null)
    val dialogUiState: StateFlow<ShowDeviceAdditionDialog?> = _dialogUiState.asStateFlow()

    private val _navigationSideEffect = MutableSharedFlow<Unit>()
    val navigationSideEffect: Flow<Unit> = _navigationSideEffect.asSharedFlow()

    private var uiJob : Job? = null
    private var deviceJob: Job? = null

    init {
        uiJob = getCurrentCompositionUseCase()
            .onEach { composition ->
                if (composition == null) {
                    _uiState.value = DeviceUiState.Error(error = "構成が設定されていません")
                    deviceJob?.cancel()
                } else {
                    compositionFlow.value = composition

                    (uiState.value as? DeviceUiState.Success)?.also {
                        _uiState.value = it.copy(composition = composition)
                    }
                }
            }
            .catch { handleError(it) }
            .launchIn(viewModelScope)

        compositionFlow
            .filterNotNull()
            .map { getCurrentDeviceTypeUseCase().first() }
            .onEach { getDeviceList(it) }
            .take(1)
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    private fun getDeviceList(deviceType: DeviceType) {
        if (uiState.value is DeviceUiState.Error) return

        deviceJob = getDeviceUseCase(deviceType).onEach {
            when (it) {
                is AppResponse.Loading -> {
                    _uiState.value = DeviceUiState.Loading
                }

                is AppResponse.Success -> {
                    val deviceList = it.data ?: emptyList()

                    devicesFlow.value = deviceList
                    _uiState.value = DeviceUiState.Success(
                        deviceType = deviceType,
                        devices = deviceList,
                        composition = compositionFlow.filterNotNull().first()
                    )
                }

                is AppResponse.Failure -> {
                    _uiState.value = DeviceUiState.Error(it.error)
                }
            }
        }
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    fun changeSortType(sort: SortType) {
        filterDeviceList(newSort = sort)
    }

    fun searchDevice(text: String) {
        filterDeviceList(newSearch = text)
    }

    private fun filterDeviceList(
        newSort: SortType? = null,
        newSearch: String? = null
    ) {
        val successState = uiState.value as? DeviceUiState.Success ?: return

        val sort = newSort ?: successState.currentDeviceSort
        val searchText = newSearch ?: successState.searchText

        _uiState.value = successState.copy(
            searchText = searchText,
            currentDeviceSort = sort,
        )
    }

    fun notifyDeviceSelected(device: Device) {
        val deviceQty = DeviceWithQty(device, 1)
        _dialogUiState.value = ShowDeviceAdditionDialog(deviceQty = deviceQty, isEdit = false)
    }

    fun notifyDeviceSelected(deviceQty: DeviceWithQty) {
        _dialogUiState.value = ShowDeviceAdditionDialog(deviceQty = deviceQty, isEdit = true)
    }

    fun clearDialog() {
        _dialogUiState.value = null
    }

    fun addAssembly(device: Device, quantity: Int = 1, isEdit: Boolean) {
        clearDialog()

        val state = uiState.value as? DeviceUiState.Success ?: return

        val assemblies = List(quantity) {
            Assembly(
                assemblyId = state.composition.assemblyId,
                assemblyName = state.composition.assemblyName,
                deviceId = device.id,
                deviceType = device.device,
                deviceName = device.name,
                deviceImgUrl = device.imgUrl,
                deviceDetail = device.detail,
                devicePriceSaved = device.price,
                devicePriceRecent = device.price,
            )
        }

        viewModelScope.launch(handler) {
            if (isEdit) {
                // 編集は画面遷移しない
                addAssemblyUseCase(assemblies)
            } else {
                // 新規追加は画面遷移する
                stopAllJob()
                addAssemblyUseCase(assemblies)
                _navigationSideEffect.emit(Unit)
            }
        }
    }

    fun deleteAssembly(device: Device, quantity: Int) {
        clearDialog()

        val state = uiState.value as? DeviceUiState.Success ?: return

        viewModelScope.launch(handler) {
            deleteAssemblyUseCase(
                assemblyId = state.composition.assemblyId,
                deviceId = device.id,
                quantity = quantity
            )
        }
    }

    private suspend fun stopAllJob() {
        uiJob?.cancelAndJoin()
        deviceJob?.cancelAndJoin()
    }

    private fun handleError(error: Throwable) {
        clearDialog()
        _uiState.value = DeviceUiState.Error(error.message)
    }
}

sealed interface DeviceUiState {
    data object Loading : DeviceUiState

    data class Success(
        val deviceType: DeviceType = DeviceType.PC_CASE,
        private val devices: List<Device>,
        val searchText: String = "",
        val currentDeviceSort: SortType = SortType.POPULARITY,
        val composition: Composition,
    ) : DeviceUiState {

        private val searchList = searchText
            .trim()
            .replace("　", " ")
            .split("\\s+".toRegex())
            .map { convertToFullWidthKatakana(it) }

        val selectedDevices: List<DeviceWithQty> = composition.items
            .filter { it.deviceType == deviceType }
            .mapNotNull { item ->
                devices.firstOrNull { item.deviceId == it.id }
                    ?.let { device -> DeviceWithQty(device, item.quantity) }
            }

        val visibleDevices: List<Device> = when (currentDeviceSort) {
            SortType.POPULARITY -> devices.sortedBy { if (it.rank > 0) it.rank else Int.MAX_VALUE }
            SortType.NEW_ARRIVAL -> devices.sortedByDescending { it.releaseDate }
            SortType.PRICE_ASC -> devices.sortedBy { if (it.price > 0) it.price else MAX_PRICE }
            SortType.PRICE_DESC -> devices.sortedByDescending { if (it.price > 0) it.price else ZERO_PRICE }
        }.filter { device ->
            searchList.all { search ->
                device.name.contains(other = search, ignoreCase = true)
                        || device.detail.contains(other = search, ignoreCase = true)
            }
        }

        private fun convertToFullWidthKatakana(input: String): String {
            val sb = StringBuilder()
            for (i in input.indices) {
                val c1 = input[i]
                if (c1 == 'ﾞ' || c1 == 'ﾟ') continue // 濁音、半濁音はskip

                if (i < input.length -1) {
                    val c2 = input[i+1]
                    val str = c1.toString() + c2.toString()
                    if ((c2 == 'ﾞ' || c2 == 'ﾟ') && KANA_HALF_TO_FULL.keys.contains(str)) {
                        sb.append(mapKanaHalfToFull(str))
                    } else {
                        sb.append(mapKanaHalfToFull(c1.toString()))
                    }
                } else {
                    sb.append(mapKanaHalfToFull(c1.toString()))
                }
            }
            return sb.toString()
        }

        private fun mapKanaHalfToFull(input: String): String {
            return KANA_HALF_TO_FULL[input] ?: input
        }
    }

    data class Error(val error: String?) : DeviceUiState
}

data class ShowDeviceAdditionDialog(val deviceQty: DeviceWithQty, val isEdit: Boolean = false)

data class DeviceWithQty(
    val device: Device,
    val quantity: Int,
)
