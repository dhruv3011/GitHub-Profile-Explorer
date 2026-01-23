package com.dhruv.feature.userrepodetail

import com.dhruv.core.domain.model.GithubRepo

sealed class RepoDetailUiState {
    object Loading : RepoDetailUiState()
    data class Success(val repo: GithubRepo,
                       val totalForksAcrossAllRepos: Int,
                       val isStarUser: Boolean) : RepoDetailUiState()
    data class Error(val message: String) : RepoDetailUiState()
}