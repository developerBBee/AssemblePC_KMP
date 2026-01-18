package jp.developer.bbee.assemblepc.common

sealed class AppResponse<T>(
    val data: T? = null,
    val error: String? = null
){
    class Success<T>(data: T) : AppResponse<T>(data = data)
    class Failure<T>(error: String) : AppResponse<T>(error = error)
    class Loading<T> : AppResponse<T>()
}
