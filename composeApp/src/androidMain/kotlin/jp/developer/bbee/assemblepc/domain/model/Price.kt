package jp.developer.bbee.assemblepc.domain.model

import jp.developer.bbee.assemblepc.domain.model.serializer.PriceSerializer
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable(with = PriceSerializer::class)
@JvmInline
value class Price(val value: Int): Comparable<Price> {

    operator fun plus(price: Price): Price = Price(value + price.value)
    operator fun plus(priceInt: Int): Price = Price(value + priceInt)
    operator fun minus(price: Price): Price = Price(value - price.value)
    operator fun minus(priceInt: Int): Price = Price(value - priceInt)
    operator fun times(price: Price): Price = Price(value * price.value)
    operator fun times(multiplier: Int): Price = Price(value * multiplier)

    override operator fun compareTo(other: Price): Int = value.compareTo(other.value)
    operator fun compareTo(priceInt: Int): Int = value.compareTo(priceInt)

    fun yen(): String = String.format(Locale.JAPAN, "¥ %,d", value)

    fun yenOrUnknown(): String = if (isZero()) "価格情報なし" else yen()

    fun isZero(): Boolean = this == ZERO_PRICE

    override fun toString(): String {
        return value.toString()
    }
}

val ZERO_PRICE = Price(0)
val MAX_PRICE = Price(Int.MAX_VALUE)

fun Int.toPrice(): Price = Price(this)

fun List<Price>.sum(): Price = Price(sumOf { it.value })

fun List<Price>.hasZero(): Boolean = any { it.isZero() }

fun List<Price>.sumYen(): String = sum().yen().appendIf(hasZero()) { "(+α)" }

private fun String.appendIf(
    condition: Boolean,
    append: () -> String
): String = if (condition) this + append() else this
