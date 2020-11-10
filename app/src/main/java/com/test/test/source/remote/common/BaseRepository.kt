package com.test.test.source.remote.common

import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T : Any> execute(block: suspend () -> Response<T>): RemoteResult<T> {
        return try {
            calculateResult(block())
        } catch (e: Exception) {
            RemoteResult.ErrorResult(e)
        }
    }

    private fun <T : Any> calculateResult(data: Response<T>): RemoteResult<T> {
        return data.code().takeIf {
            it !in CODE_SUCCESS_START..CODE_SUCCESS_END
        }?.let {
            RemoteResult.ErrorResult<T>(
                IllegalArgumentException(
                    "${it}: ${data.errorBody()?.string()}"
                )
            )
        } ?: run {
            data.body()?.let {
                RemoteResult.SuccessResult(it)
            } ?: run {
                RemoteResult.ErrorResult<T>(NullPointerException("Response is empty"))
            }
        }
    }

    companion object {
        private const val CODE_SUCCESS_START = 200
        private const val CODE_SUCCESS_END = 299
    }
}