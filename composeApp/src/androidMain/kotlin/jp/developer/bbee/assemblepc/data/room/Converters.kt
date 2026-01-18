package jp.developer.bbee.assemblepc.data.room

import androidx.room.TypeConverter
import jp.developer.bbee.assemblepc.domain.model.Price

class Converters {
    @TypeConverter
    fun fromPrice(price: Price): Int {
        return price.value
    }

    @TypeConverter
    fun toPrice(value: Int): Price {
        return Price(value)
    }
}
