package jp.developer.bbee.assemblepc.data.remote


import jp.developer.bbee.assemblepc.domain.model.ZERO_PRICE
import jp.developer.bbee.assemblepc.domain.model.toPrice
import jp.developer.bbee.assemblepc.data.room.model.Device
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    val results: List<Result>?
) {
    fun toDevice(): List<Device> {
        if (results == null) return emptyList()
        return results.map {
            Device(
                id = it.id,
                device = it.device,
                name = it.name ?: EMPTY_TEXT,
                imgurl = it.imgurl ?: DEFAULT_IMG_URL,
                invisible = when (it.invisible) {
                    null -> true
                    1 -> true
                    else -> false
                },
                url = it.url ?: DEFAULT_URL,
                detail = it.detail ?: EMPTY_TEXT,
                price = it.price?.toPrice() ?: ZERO_PRICE,
                rank = it.rank ?: DEFAULT_RANK,
                releasedate = it.releasedate ?: DEFAULT_RELEASE_DATE,
                flag1 = it.flag1,
                flag2 = it.flag2,
                createddate = it.createddate,
                lastupdate = it.lastupdate
            )
        }
    }

    companion object {
        private const val EMPTY_TEXT = ""
        private const val DEFAULT_IMG_URL = "https://img1.kakaku.k-img.com/images/productimage/l/nowprinting.gif"
        private const val DEFAULT_URL = "https://kakaku.com/"
        private const val DEFAULT_RELEASE_DATE = "20000101"
        private const val DEFAULT_RANK = 99
    }
}

@Serializable
data class Result(
    val createddate: String?,
    val detail: String?,
    val device: String,
    val flag1: Int?,
    val flag2: Int?,
    val id: String,
    val imgurl: String?,
    val invisible: Int?,
    val lastupdate: String?,
    val name: String?,
    val price: Int?,
    val rank: Int?,
    val releasedate: String?,
    val url: String?
)
