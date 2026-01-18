package jp.developer.bbee.assemblepc.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.developer.bbee.assemblepc.data.remote.AssemblePcApi
import jp.developer.bbee.assemblepc.data.repository.CurrentCompositionRepositoryImpl
import jp.developer.bbee.assemblepc.data.repository.CurrentDeviceTypeRepositoryImpl
import jp.developer.bbee.assemblepc.data.repository.DeviceRepositoryImpl
import jp.developer.bbee.assemblepc.data.repository.ReviewRepositoryImpl
import jp.developer.bbee.assemblepc.data.room.AssemblyDeviceDao
import jp.developer.bbee.assemblepc.domain.repository.CurrentCompositionRepository
import jp.developer.bbee.assemblepc.domain.repository.CurrentDeviceTypeRepository
import jp.developer.bbee.assemblepc.domain.repository.DeviceRepository
import jp.developer.bbee.assemblepc.domain.repository.ReviewRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDeviceRepository(
        api: AssemblePcApi,
        dao: AssemblyDeviceDao
    ): DeviceRepository = DeviceRepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideCurrentCompositionRepository(
        @ApplicationContext context: Context,
    ): CurrentCompositionRepository = CurrentCompositionRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideCurrentDeviceTypeRepository(
        @ApplicationContext context: Context,
    ): CurrentDeviceTypeRepository = CurrentDeviceTypeRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideReviewRepository(
        api: AssemblePcApi,
    ): ReviewRepository = ReviewRepositoryImpl(api)
}
