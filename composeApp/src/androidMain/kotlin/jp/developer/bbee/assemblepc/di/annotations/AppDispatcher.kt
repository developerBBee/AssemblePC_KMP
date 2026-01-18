package jp.developer.bbee.assemblepc.di.annotations

import jp.developer.bbee.assemblepc.di.enums.DispatcherType
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class AppDispatcher(val dispatcherType: DispatcherType)
