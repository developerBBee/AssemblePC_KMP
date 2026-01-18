package jp.developer.bbee.assemblepc.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
