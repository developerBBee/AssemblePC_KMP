package jp.developer.bbee.assemblepc.data.remote


import kotlinx.serialization.Serializable

@Serializable
data class UpdateDto(
    val kakakuupdate: Int?
)

fun UpdateDto.toIntUpdate(): Int {
    return kakakuupdate ?: 0
}