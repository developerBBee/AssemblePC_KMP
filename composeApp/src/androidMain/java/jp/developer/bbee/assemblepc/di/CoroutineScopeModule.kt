package jp.developer.bbee.assemblepc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.developer.bbee.assemblepc.di.annotations.AppCoroutineScope
import jp.developer.bbee.assemblepc.di.annotations.AppDispatcher
import jp.developer.bbee.assemblepc.di.enums.DispatcherType.DEFAULT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Singleton
    @Provides
    @AppCoroutineScope
    fun provideCoroutineScope(
        @AppDispatcher(DEFAULT) appDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + appDispatcher)
}
