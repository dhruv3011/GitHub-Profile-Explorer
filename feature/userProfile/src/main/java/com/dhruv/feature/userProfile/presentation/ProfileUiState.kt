package com.dhruv.feature.userProfile.presentation

import com.dhruv.core.domain.model.GithubUser

sealed class ProfileUiState {
    object Idle : ProfileUiState()

    object Loading : ProfileUiState()

    data class Success(val user: GithubUser) : ProfileUiState()

    data class Error(val message: String) : ProfileUiState()
}