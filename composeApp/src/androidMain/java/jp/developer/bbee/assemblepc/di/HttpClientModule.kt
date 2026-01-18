package jp.developer.bbee.assemblepc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    private const val CONNECT_TIMEOUT_SECOND: Long = 30L
    private const val READ_TIMEOUT_SECOND: Long = 30L
    private const val CALL_TIMEOUT_SECOND: Long = 30L

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECOND))
            .readTimeout(Duration.ofSeconds(READ_TIMEOUT_SECOND))
            .callTimeout(Duration.ofSeconds(CALL_TIMEOUT_SECOND))
            .build()
    }
}