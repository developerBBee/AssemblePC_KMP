package jp.developer.bbee.assemblepc.shared.presentation.common

import android.content.res.Resources

actual fun getScreenWidthDp(): Int {
    return Resources.getSystem().configuration.screenWidthDp
}