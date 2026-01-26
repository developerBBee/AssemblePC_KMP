package jp.developer.bbee.assemblepc.shared.presentation.common

import android.content.res.Resources

internal actual fun getScreenWidthDp(): Int {
    return Resources.getSystem().configuration.screenWidthDp
}