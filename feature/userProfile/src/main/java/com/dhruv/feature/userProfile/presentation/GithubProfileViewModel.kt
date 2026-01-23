package com.dhruv.feature.userProfile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.core.common.utils.ServerResponse
import com.dhruv.core.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onSearchClicked(username: String) {
        if (username.isBlank()) return
        
        viewModelScope.launch {
            getUserProfileUseCase(username).collect { result ->
                when(result) {
                    is ServerResponse.Loading -> _uiState.value = ProfileUiState.Loading
                    is ServerResponse.Error -> _uiState.value = ProfileUiState.Error(result.message ?: "Unknown Error")
                    is ServerResponse.Success -> {
                        _uiState.value = result.data?.let { user->
                            ProfileUiState.Success(user)
                        } ?: ProfileUiState.Error( "User data is empty")
                    }
                }
            }
        }
    }
}