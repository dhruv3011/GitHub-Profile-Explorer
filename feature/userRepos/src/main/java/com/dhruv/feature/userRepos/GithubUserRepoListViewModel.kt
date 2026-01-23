package com.dhruv.feature.userRepos

import androidx.lifecycle.ViewModel
import com.dhruv.core.common.utils.ServerResponse
import com.dhruv.core.domain.model.GithubRepo
import com.dhruv.core.domain.usecase.GetUserReposUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubUserRepoListViewModel @Inject constructor(
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {
    private val _reposState = MutableStateFlow<RepoListUiState>(RepoListUiState.Idle)
    val reposState = _reposState.asStateFlow()

    private var lastLoadedUsername: String? = null
    var animationShown = false

    fun loadRepos(username: String) {
        if (lastLoadedUsername != username) {
            animationShown = false
            lastLoadedUsername = username
            viewModelScope.launch {
                getUserReposUseCase(username).collect { result ->
                    when (result) {
                        is ServerResponse.Loading -> _reposState.value = RepoListUiState.Loading
                        is ServerResponse.Success -> {
                            val data = result.data ?: emptyList()
                            _reposState.value = RepoListUiState.Success(data)
                        }
                        is ServerResponse.Error -> {
                            _reposState.value = RepoListUiState.Error(result.message ?: "Error")
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}