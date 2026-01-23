package com.dhruv.core.network.api

import com.dhruv.core.network.model.GithubRepoDto
import com.dhruv.core.network.model.GithubUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("/users/{username}")
    suspend fun getUserProfile(@Path("username") username: String): Response<GithubUserDto>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): Response<List<GithubRepoDto>>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<GithubRepoDto>
}