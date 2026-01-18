package jp.developer.bbee.assemblepc.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.developer.bbee.assemblepc.domain.model.Price

@Entity
data class Device(
    @PrimaryKey val id: String,
    val device: String, // DeviceTypeのkeyになる
    val name: String,
    val imgurl: String,
    val url: String,
    val detail: String,
    val price: Price,
    val rank: Int,
    val releasedate: String,
    val invisible: Boolean,
    val flag1: Int?,
    val flag2: Int?,
    val createddate: String?,
    val lastupdate: String?
)
