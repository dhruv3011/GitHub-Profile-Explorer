package com.dhruv.core.data.repository

import com.dhruv.core.common.utils.DispatcherProvider
import com.dhruv.core.data.mapper.toDomain
import com.dhruv.core.domain.model.GithubRepo
import com.dhruv.core.domain.model.GithubUser
import com.dhruv.core.domain.repository.GithubRepository
import com.dhruv.core.network.api.GithubApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val dispatchers: DispatcherProvider
) : GithubRepository {
    override suspend fun getUserProfile(userName: String): GithubUser  =
        withContext(dispatchers.io) {
            val response = api.getUserProfile(userName)
            if (response.isSuccessful) {
                response.body()?.toDomain() ?: throw Exception("Empty body")
            } else {
                val errorMessage = when (response.code()) {
                    404 -> "User $userName not found"
                    403 -> "GitHub API rate limit exceeded"
                    else -> throw Exception("Server Error: ${response.code()}")
                }
                throw Exception(errorMessage)
            }

        }

    override suspend fun getUserRepos(userName: String): List<GithubRepo> =
        withContext(dispatchers.io) {
            val response = api.getUserRepos(userName)
            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: emptyList()
            } else {
                val errorMessage = when (response.code()) {
                    403 -> "GitHub API rate limit exceeded"
                    else -> throw Exception("Server Error: ${response.code()}")
                }
                throw Exception(errorMessage)
            }
    }

    override suspend fun getRepoDetails(owner: String, repo: String): GithubRepo =
        withContext(dispatchers.io) {
            val response = api.getRepoDetails(owner, repo)
            if (response.isSuccessful) {
                response.body()?.toDomain() ?: throw Exception("Empty body")
            } else {
                val errorBody = response.errorBody()?.string()
                throw Exception("Error ${response.code()}: $errorBody")
            }
        }
}