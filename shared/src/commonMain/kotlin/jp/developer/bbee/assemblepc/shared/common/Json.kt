package jp.developer.bbee.assemblepc.shared.common

import kotlinx.serialization.json.Json

val defaultJson = Json {
    ignoreUnknownKeys = true // デシリアライズ時に未知のプロパティを無視する
    encodeDefaults = true // シリアル化の際にデフォルト値を含める
}
