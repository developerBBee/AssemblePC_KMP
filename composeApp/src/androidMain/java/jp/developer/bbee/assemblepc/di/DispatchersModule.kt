package jp.developer.bbee.assemblepc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.developer.bbee.assemblepc.di.annotations.AppDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import jp.developer.bbee.assemblepc.di.enums.DispatcherType.IO
import jp.developer.bbee.assemblepc.di.enums.DispatcherType.DEFAULT

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @AppDispatcher(IO)
    @Singleton
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @AppDispatcher(DEFAULT)
    @Singleton
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
