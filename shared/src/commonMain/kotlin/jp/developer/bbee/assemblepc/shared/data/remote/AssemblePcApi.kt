package jp.developer.bbee.assemblepc.shared.data.remote

import jp.developer.bbee.assemblepc.shared.data.remote.request.ReviewRequest
import jp.developer.bbee.assemblepc.shared.data.remote.response.ReviewResponse

interface AssemblePcApi {
//    @GET("/api/update")
    suspend fun getLastUpdate(): UpdateDto
//    @GET("/api/devicelist")
    suspend fun getDeviceList(device: String): DeviceDto

//    @POST("/api/gemini/review")
    suspend fun review(request: ReviewRequest): ReviewResponse
}
