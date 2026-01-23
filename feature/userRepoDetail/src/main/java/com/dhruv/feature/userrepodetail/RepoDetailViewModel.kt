package com.dhruv.feature.userrepodetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.core.common.utils.ServerResponse
import com.dhruv.core.domain.model.GithubRepo
import com.dhruv.core.domain.usecase.GetRepoDetailsUseCase
import com.dhruv.core.domain.usecase.GetUserReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase,
    private val getUserReposUseCase: GetUserReposUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val userName: String = checkNotNull(savedStateHandle["owner"])
    private val repoName: String = checkNotNull(savedStateHandle["repo"])

    private val _uiState = MutableStateFlow<RepoDetailUiState>(RepoDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                getRepoDetailsUseCase(userName, repoName),
                getUserReposUseCase(userName)
            ) { repoDetail: ServerResponse<GithubRepo>, allRepoList: ServerResponse<List<GithubRepo>> ->
                when {
                    repoDetail is ServerResponse.Success && allRepoList is ServerResponse.Success -> {
                        val repoData = repoDetail.data!!
                        val totalForks = allRepoList.data?.sumOf { it.forksCount } ?: 0

                        RepoDetailUiState.Success(
                            repo = repoData,
                            totalForksAcrossAllRepos = totalForks,
                            isStarUser = totalForks > 5000
                        )
                    }

                    repoDetail is ServerResponse.Error -> {
                        RepoDetailUiState.Error(repoDetail.message ?: "Failed to load repo details")
                    }
                    allRepoList is ServerResponse.Error -> {
                        RepoDetailUiState.Error(allRepoList.message ?: "Failed to load fork stats")
                    }

                    else -> RepoDetailUiState.Loading
                }
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

}