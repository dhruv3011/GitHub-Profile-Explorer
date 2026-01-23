package com.dhruv.feature.userRepos

import com.dhruv.core.domain.model.GithubRepo

sealed interface RepoListUiState {
    data object Idle : RepoListUiState
    data object Loading : RepoListUiState
    data class Success(val repos: List<GithubRepo>) : RepoListUiState
    data class Error(val message: String) : RepoListUiState
}