package com.dhruv.feature.userProfile.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dhruv.core.ui.components.RoundedTextField
import com.dhruv.feature.userRepos.ProfileResultContent


@Composable
fun ProfileScreen(
    viewModel: GithubProfileViewModel = hiltViewModel(),
    onRepoClick: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            RoundedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                labelText = "Search UserName",
                leadingIcon = Icons.Default.Search,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
                keyboardActions = KeyboardActions (
                    onSearch =  {
                        if (searchQuery.isNotBlank()) {
                            viewModel.onSearchClicked(searchQuery)
                            keyboardController?.hide()
                        }
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is ProfileUiState.Idle -> {
                Text("Search for a user to see their profile", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is ProfileUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is ProfileUiState.Error -> {
                Text(text = state.message, color = Color.Red)
            }
            is ProfileUiState.Success -> {
                val headerVisible = remember { MutableTransitionState(false) }.apply { targetState = true }

                AnimatedVisibility(
                    visibleState = headerVisible,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { -50 })
                ) {
                    ProfileHeader(state.user)
                }

                ProfileResultContent(
                    username = state.user.login,
                    onRepoClick = onRepoClick
                )
            }
        }
    }
}