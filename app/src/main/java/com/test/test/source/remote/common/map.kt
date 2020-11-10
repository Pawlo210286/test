package com.test.test.source.remote.common

fun <T : Any, M : Any> RemoteResult<T>.map(block: (T) -> M): RemoteResult<M> {
    return when (this) {
        is RemoteResult.ErrorResult<*> -> {
            RemoteResult.ErrorResult(this.error)
        }
        is RemoteResult.SuccessResult<*> -> {
            try {
                val mapped = block(this.data as T)
                RemoteResult.SuccessResult(mapped)
            } catch (e: Exception) {
                RemoteResult.ErrorResult<M>(e)
            }
        }
        else -> {
            RemoteResult.ErrorResult(IllegalArgumentException())
        }
    }
}