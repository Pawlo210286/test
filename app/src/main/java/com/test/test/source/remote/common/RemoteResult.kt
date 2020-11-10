package com.test.test.source.remote.common

sealed class RemoteResult<T : Any> {
    class SuccessResult<T : Any>(
        val data: T
    ) : RemoteResult<T>()

    class ErrorResult<T : Any>(
        val error: Throwable
    ) : RemoteResult<T>()
}