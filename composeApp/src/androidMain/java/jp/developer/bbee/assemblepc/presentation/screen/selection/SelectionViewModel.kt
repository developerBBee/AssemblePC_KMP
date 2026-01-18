package jp.developer.bbee.assemblepc.presentation.screen.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.use_case.SaveCurrentDeviceTypeUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionViewModel @Inject constructor(
    private val saveCurrentDeviceTypeUseCase: SaveCurrentDeviceTypeUseCase,
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, ex -> handleError() }

    private val _navigationSideEffect = MutableSharedFlow<SelectionSideEffect>()
    val navigationSideEffect: Flow<SelectionSideEffect> = _navigationSideEffect.asSharedFlow()

    fun saveCurrentDeviceType(deviceType: DeviceType) {
        viewModelScope.launch(handler) {
            saveCurrentDeviceTypeUseCase(deviceType)
            _navigationSideEffect.emit(SelectionSideEffect.NextScreen)
        }
    }

    private fun handleError() {
        viewModelScope.launch {
            _navigationSideEffect.emit(SelectionSideEffect.Error)
        }
    }
}

enum class SelectionSideEffect {
    NextScreen,
    Error,
}
