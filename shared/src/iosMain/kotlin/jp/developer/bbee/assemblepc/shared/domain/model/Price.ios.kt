package jp.developer.bbee.assemblepc.shared.domain.model

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.localeWithLocaleIdentifier
import platform.Foundation.numberWithLong

actual fun Price.yen(): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        currencyCode = "JPY"
        locale = NSLocale.localeWithLocaleIdentifier("ja_JP")
        maximumFractionDigits = 0UL
        minimumFractionDigits = 0UL
    }
    val nsNumber = NSNumber.numberWithLong(value.toLong())
    return formatter.stringFromNumber(nsNumber) ?: "¥$value"
}
