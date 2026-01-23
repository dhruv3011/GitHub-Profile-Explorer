package com.dhruv.core.domain.usecase

import com.dhruv.core.common.utils.ServerResponse
import com.dhruv.core.domain.model.GithubRepo
import com.dhruv.core.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor(
    private val githubRepository: GithubRepository) {
    operator fun invoke (owner: String, repo: String): Flow<ServerResponse<GithubRepo>> = flow {
        emit(ServerResponse.Loading())
        try {
            val user = githubRepository.getRepoDetails(owner, repo)
            emit(ServerResponse.Success(user))
        } catch (e: Exception) {
            emit(ServerResponse.Error(e.message ?: "Unknown Error"))
        }
    }
}