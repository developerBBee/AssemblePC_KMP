package jp.developer.bbee.assemblepc.shared.presentation.screen.device

import androidx.lifecycle.viewModelScope
import jp.developer.bbee.assemblepc.shared.common.AppResponse
import jp.developer.bbee.assemblepc.shared.common.Constants.KANA_HALF_TO_FULL
import jp.developer.bbee.assemblepc.shared.domain.model.Assembly
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.model.Device
import jp.developer.bbee.assemblepc.shared.domain.model.MAX_PRICE
import jp.developer.bbee.assemblepc.shared.domain.model.ZERO_PRICE
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.domain.use_case.AddAssemblyUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.DeleteAssemblyUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetCurrentDeviceTypeUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetDeviceUseCase
import jp.developer.bbee.assemblepc.shared.presentation.common.BaseViewModel
import jp.developer.bbee.assemblepc.shared.presentation.screen.device.components.SortType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class DeviceViewModel(
    getCurrentCompositionUseCase: GetCurrentCompositionUseCase,
    private val getCurrentDeviceTypeUseCase: GetCurrentDeviceTypeUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val addAssemblyUseCase: AddAssemblyUseCase,
    private val deleteAssemblyUseCase: DeleteAssemblyUseCase,
) : BaseViewModel() {
    private val handler = CoroutineExceptionHandler { _, ex -> handleError(ex) }

    private val compositionFlow = MutableStateFlow<Composition?>(null)

    private val _uiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Loading)
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    private val _dialogUiState = MutableStateFlow<ShowDeviceAdditionDialog?>(null)
    val dialogUiState: StateFlow<ShowDeviceAdditionDialog?> = _dialogUiState.asStateFlow()

    private val _navigationSideEffect = MutableSharedFlow<Unit>()
    val navigationSideEffect: Flow<Unit> = _navigationSideEffect.asSharedFlow()

    private var deviceJob: Job? = null

    init {
        getCurrentCompositionUseCase()
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
            .launchIn(viewModelScope + handler)

        compositionFlow
            .filterNotNull()
            .onEach { observeDevice() }
            .launchIn(viewModelScope + handler)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeDevice() {
        deviceJob?.cancel()
        deviceJob = getCurrentDeviceTypeUseCase()
            .flatMapLatest { deviceType -> getDeviceUseCase(deviceType) }
            .onEach { response ->
                _uiState.value = when (response) {
                    is AppResponse.Loading -> DeviceUiState.Loading

                    is AppResponse.Success -> {
                        val deviceList = response.data ?: emptyList()

                        DeviceUiState.Success(
                            deviceType = deviceList.first().deviceType,
                            devices = deviceList,
                            composition = compositionFlow.filterNotNull().first(),
                        )
                    }

                    is AppResponse.Failure -> DeviceUiState.Error(response.error)
                }
            }
            .launchIn(viewModelScope + handler)
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

    fun addAssembly(
        device: Device,
        quantity: Int,
        isEdit: Boolean,
    ) {
        clearDialog()

        val state = uiState.value as? DeviceUiState.Success ?: return

        val assemblies = List(quantity) {
            Assembly(
                assemblyId = state.composition.assemblyId,
                assemblyName = state.composition.assemblyName,
                deviceId = device.id,
                deviceType = device.deviceType,
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
                quantity = quantity,
            )
        }
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
