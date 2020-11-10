package com.test.test.source.remote.repository.reps

import com.test.test.source.remote.model.response.RepResponse
import com.test.test.source.remote.model.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepsApi {

    @GET("orgs/octokit/repos")
    suspend fun getRepList(): Response<List<RepResponse>>

    @GET("search/repositories")
    suspend fun search(
        @Query("q")
        query: String,
        @Query("per_page")
        perPage: Int,
        @Query("page")
        page: Int
    ): Response<SearchResponse>
}