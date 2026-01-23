package com.dhruv.feature.userRepos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dhruv.core.domain.model.GithubRepo

@Composable
fun ProfileResultContent(
    username: String,
    viewModel: GithubUserRepoListViewModel = hiltViewModel(),
    onRepoClick: (String, String) -> Unit
) {

    LaunchedEffect(username) {
        viewModel.loadRepos(username)
    }

    val repos by viewModel.reposState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        when (repos) {
            is RepoListUiState.Loading -> {

            }
            is RepoListUiState.Success -> {
                val repos = (repos as RepoListUiState.Success).repos
                if (repos.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No repositories found for $username",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        itemsIndexed(repos) { index, repo ->
                            if(viewModel.animationShown) {
                                RepoItem(repo = repo, onClick = { onRepoClick(repo.ownerName, repo.name) })
                            } else {
                                val itemVisible = remember { MutableTransitionState(false) }
                                    .apply { targetState = true }
                                AnimatedVisibility(
                                    visibleState = itemVisible,
                                    enter = fadeIn(
                                        animationSpec = tween(durationMillis = 500, delayMillis = index * 80)
                                    ) + slideInVertically(
                                        animationSpec = tween(durationMillis = 500, delayMillis = index * 80),
                                        initialOffsetY = { it / 2 }
                                    )
                                ) {
                                    RepoItem(repo = repo, onClick = { onRepoClick(repo.ownerName, repo.name) })
                                }
                            }

                            if (index == 0 && !viewModel.animationShown) {
                                SideEffect { viewModel.animationShown = true }
                            }
                        }
                    }
                }
            }
            is RepoListUiState.Error -> {
                Text("Error loading repos", color = Color.Red)
            }
            else -> {}
        }
    }
}

@Composable
fun RepoItem(
    repo: GithubRepo,
    onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            repo.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "⭐ ${repo.stargazersCount}", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "🍴 ${repo.forksCount}", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}