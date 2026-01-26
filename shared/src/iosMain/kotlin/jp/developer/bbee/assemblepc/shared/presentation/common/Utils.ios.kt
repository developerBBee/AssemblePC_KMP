package jp.developer.bbee.assemblepc.shared.presentation.common

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
internal actual fun getScreenWidthDp(): Int {
    val rect = UIScreen.mainScreen.bounds
    return CGRectGetWidth(rect).toInt()
}