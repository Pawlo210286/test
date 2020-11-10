package com.test.test.source.remote.repository.reps

import com.test.test.feature.main.api.IMainRemote
import com.test.test.feature.main.api.model.Rep
import com.test.test.source.remote.common.BaseRepository
import com.test.test.source.remote.common.RemoteResult
import com.test.test.source.remote.common.map
import com.test.test.source.remote.model.response.RepResponse

class RepsRepository(
    private val api: RepsApi
) : BaseRepository(), IMainRemote {

    override suspend fun getRepList(): RemoteResult<List<Rep>> {
        return execute {
            api.getRepList()
        }.map { list ->
            list.map(this::mapRep)
        }
    }

    override suspend fun search(query: String, page: Int): RemoteResult<List<Rep>> {
        return execute {
            api.search(
                query = query,
                page = page,
                perPage = SEARCH_PER_PAGE
            )
        }.map {
            it.items.map(this::mapRep)
        }
    }

    private fun mapRep(it: RepResponse): Rep {
        return Rep(
            id = it.id,
            name = it.name,
            createdAt = it.createdAt
        )
    }

    companion object {
        private const val SEARCH_PER_PAGE = 15
    }
}