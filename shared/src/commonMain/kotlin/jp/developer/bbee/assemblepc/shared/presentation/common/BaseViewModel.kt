package jp.developer.bbee.assemblepc.shared.presentation.common

import androidx.lifecycle.ViewModel
import io.github.aakira.napier.Napier

abstract class BaseViewModel : ViewModel() {

    init {
        Napier.d(message = "init()", tag = this::class.simpleName)
    }

    override fun onCleared() {
        super.onCleared()
        Napier.d(message = "onCleared()", tag = this::class.simpleName)
    }
}
