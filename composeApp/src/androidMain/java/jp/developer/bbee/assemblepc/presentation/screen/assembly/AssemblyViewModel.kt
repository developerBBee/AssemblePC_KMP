package jp.developer.bbee.assemblepc.presentation.screen.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.model.CompositionItem
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.domain.use_case.AddAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.DeleteAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.presentation.screen.device.DeviceWithQty
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssemblyViewModel @Inject constructor(
    getCurrentCompositionUseCase: GetCurrentCompositionUseCase,
    private val deleteAssemblyUseCase: DeleteAssemblyUseCase,
    private val addAssemblyUseCase: AddAssemblyUseCase,
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, ex -> handleError(ex) }

    private val _uiState = MutableStateFlow<AssemblyUiState>(AssemblyUiState.Loading)
    val uiState: StateFlow<AssemblyUiState> = _uiState.asStateFlow()

    private val _dialogUiState = MutableStateFlow<ShowAssemblyDialog?>(null)
    val dialogUiState: StateFlow<ShowAssemblyDialog?> = _dialogUiState.asStateFlow()

    init {
        getCurrentCompositionUseCase()
            .onEach { composition ->
                _uiState.value = if (composition == null) {
                    clearDialog()
                    AssemblyUiState.Error(error = "構成が設定されていません")
                } else {
                    val sortedComposition = composition
                        .copy(items = composition.items.sortedBy { it.deviceType.ordinal })
                    AssemblyUiState.ShowComposition(composition = sortedComposition)
                }
            }
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    fun addAssembly(device: Device, quantity: Int) {
        clearDialog()

        val state = _uiState.value as? AssemblyUiState.ShowComposition ?: return

        val assemblies = List(quantity) {
            device.toAssembly(
                assemblyId = state.composition.assemblyId,
                assemblyName = state.composition.assemblyName
            )
        }

        viewModelScope.launch(handler) {
            addAssemblyUseCase(assemblies = assemblies)
        }
    }

    fun deleteAssembly(device: Device, quantity: Int) {
        clearDialog()

        val state = _uiState.value as? AssemblyUiState.ShowComposition ?: return

        viewModelScope.launch(handler) {
            deleteAssemblyUseCase(
                assemblyId = state.composition.assemblyId,
                deviceId = device.id,
                quantity = quantity
            )
        }
    }

    fun showAssemblyDialog(compositionItem: CompositionItem) {

        val deviceWithQty = DeviceWithQty(
            device = compositionItem.toDevice(),
            quantity = compositionItem.quantity
        )

        _dialogUiState.value = ShowAssemblyDialog(deviceWithQty)
    }

    fun clearDialog() {
        _dialogUiState.value = null
    }

    private fun handleError(error: Throwable) {
        clearDialog()
        _uiState.value = AssemblyUiState.Error(error = error.message)
    }
}

sealed interface AssemblyUiState {
    data object Loading : AssemblyUiState
    data class ShowComposition(val composition: Composition) : AssemblyUiState
    data class Error(val error: String?) : AssemblyUiState
}

data class ShowAssemblyDialog(val deviceQty: DeviceWithQty)
