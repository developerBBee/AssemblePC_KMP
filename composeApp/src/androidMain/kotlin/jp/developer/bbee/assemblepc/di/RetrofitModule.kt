package jp.developer.bbee.assemblepc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.developer.bbee.assemblepc.common.Constants.BASE_URL
import jp.developer.bbee.assemblepc.common.defaultJson
import jp.developer.bbee.assemblepc.data.remote.AssemblePcApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideAssemblePcApi(client: OkHttpClient): AssemblePcApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(
                defaultJson.asConverterFactory("application/json".toMediaType())
            )
            .build().create(AssemblePcApi::class.java)
    }
}