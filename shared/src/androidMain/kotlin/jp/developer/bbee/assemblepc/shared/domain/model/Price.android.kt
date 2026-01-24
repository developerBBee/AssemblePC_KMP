package jp.developer.bbee.assemblepc.shared.domain.model

import java.util.Locale

actual fun Price.yen(): String = String.format(Locale.JAPAN, "¥ %,d", value)
