package com.test.test.feature.main.api

import com.test.test.feature.main.api.model.Rep
import com.test.test.source.remote.common.RemoteResult

interface IMainRemote {
    suspend fun getRepList(): RemoteResult<List<Rep>>
    suspend fun search(query: String, page: Int): RemoteResult<List<Rep>>
}