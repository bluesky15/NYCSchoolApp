package d11012025.lalitbehera.nycschools

sealed class NetworkResult<out T> {
    object LOADING : NetworkResult<Nothing>()
    data class SUCCESS<out T>(val data: T) : NetworkResult<T>()
    data class ERROR<Nothing>(val e: Throwable) : NetworkResult<Nothing>()
}