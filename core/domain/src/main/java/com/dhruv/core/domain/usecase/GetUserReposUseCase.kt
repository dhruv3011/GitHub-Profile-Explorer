package com.dhruv.core.domain.usecase

import com.dhruv.core.common.utils.ServerResponse
import com.dhruv.core.domain.model.GithubRepo
import com.dhruv.core.domain.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GithubRepository
){
    operator fun invoke(userName: String): Flow<ServerResponse<List<GithubRepo>>> = flow {
        emit(ServerResponse.Loading())
        try {
            val user = repository.getUserRepos(userName)
            emit(ServerResponse.Success(user))
        } catch (e: Exception) {
            emit(ServerResponse.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)
}