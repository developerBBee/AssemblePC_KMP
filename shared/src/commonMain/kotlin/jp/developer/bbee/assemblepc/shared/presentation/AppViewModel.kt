package jp.developer.bbee.assemblepc.shared.presentation

import androidx.lifecycle.viewModelScope
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.use_case.ClearCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.ClearCurrentDeviceTypeUseCase
import jp.developer.bbee.assemblepc.shared.domain.use_case.GetCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.shared.presentation.common.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.plus

class AppViewModel(
    getCurrentCompositionUseCase: GetCurrentCompositionUseCase,
    clearCurrentCompositionUseCase: ClearCurrentCompositionUseCase,
    clearCurrentDeviceTypeUseCase: ClearCurrentDeviceTypeUseCase,
) : BaseViewModel() {
    private val handler = CoroutineExceptionHandler { _, _ -> handleError() }

    private val _uiState = MutableStateFlow<AppUiState>(AppUiState.NoSelected)
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        getCurrentCompositionUseCase()
            .onStart {
                // アプリが立ち上がった時に、DeviceTypeの選択状態をクリアする
                clearCurrentDeviceTypeUseCase()

                // アプリが立ち上がった時に、空の構成が選択されている場合はクリアする
                val composition = getCurrentCompositionUseCase().first()
                if (composition != null && composition.items.isEmpty()) {
                    clearCurrentCompositionUseCase()
                }
            }
            .onEach { composition ->
                if (composition == null) {
                    _uiState.value = AppUiState.NoSelected
                } else {
                    _uiState.value = AppUiState.Selected(composition)
                }
            }
            .launchIn(viewModelScope + handler)
    }

    private fun handleError() {
        _uiState.value = AppUiState.NoSelected
    }
}

sealed interface AppUiState {
    data object NoSelected: AppUiState
    data class Selected(val composition: Composition): AppUiState
}
