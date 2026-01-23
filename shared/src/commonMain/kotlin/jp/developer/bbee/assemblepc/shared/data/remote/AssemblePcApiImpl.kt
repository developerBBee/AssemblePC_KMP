package jp.developer.bbee.assemblepc.shared.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import jp.developer.bbee.assemblepc.shared.common.Constants
import jp.developer.bbee.assemblepc.shared.common.defaultJson
import jp.developer.bbee.assemblepc.shared.data.remote.request.ReviewRequest
import jp.developer.bbee.assemblepc.shared.data.remote.response.ReviewResponse

class AssemblePcApiImpl : AssemblePcApi {

    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(defaultJson)
        }
    }

    override suspend fun getLastUpdate(): UpdateDto {
        return client.get {
            buildUrl("api/update")
        }.body()
    }

    override suspend fun getDeviceList(device: String): DeviceDto {
        return client.get {
            buildUrl("api/devicelist")
            parameter("device", device)
        }.body()
    }

    override suspend fun review(request: ReviewRequest): ReviewResponse {
        return client.post {
            buildUrl("api/gemini/review")
            buildHeader()
            setBody(request)
        }.body()
    }

    private fun HttpRequestBuilder.buildUrl(path: String) {
        url {
            takeFrom(urlString = Constants.BASE_URL)
            encodedPath = path
        }
    }

    private fun HttpRequestBuilder.buildHeader() {
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }
    }
}
