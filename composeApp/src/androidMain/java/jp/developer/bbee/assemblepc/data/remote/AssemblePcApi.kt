package jp.developer.bbee.assemblepc.data.remote

import jp.developer.bbee.assemblepc.data.remote.request.ReviewRequest
import jp.developer.bbee.assemblepc.data.remote.response.ReviewResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AssemblePcApi {
    @GET("/api/update")
    suspend fun getLastUpdate(): UpdateDto
    @GET("/api/devicelist")
    suspend fun getDeviceList(@Query("device") device: String): DeviceDto

    @POST("/api/gemini/review")
    suspend fun review(@Body request: ReviewRequest): ReviewResponse
}
