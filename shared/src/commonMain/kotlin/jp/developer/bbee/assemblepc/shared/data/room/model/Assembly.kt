package jp.developer.bbee.assemblepc.shared.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.developer.bbee.assemblepc.shared.domain.model.Price

@Entity
data class Assembly(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assemblyId: Int,
    val assemblyName: String,
    val deviceId: String,
    val deviceType: String,
    val deviceName : String,
    val deviceImgUrl: String,
    val deviceDetail: String,
    val devicePriceSaved: Price,
    val devicePriceRecent: Price,
    val reviewText: String? = null,
    val reviewTime: String? = null,
    @ColumnInfo(defaultValue = "2025-01-01T00:00:00.000000")
    val updatedAt: String,
)
