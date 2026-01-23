package com.dhruv.githubprofileexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dhruv.feature.userProfile.presentation.ProfileScreen
import com.dhruv.feature.userrepodetail.RepoDetailScreen


@Composable
fun GithubNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ProfileRoute
    ) {
        composable<ProfileRoute> {
            ProfileScreen(
                onRepoClick = { user, repo ->
                    navController.navigate(
                        RepoDetailRoute(owner = user, repo = repo)
                    )
                }
            )
        }

        composable<RepoDetailRoute> {
            RepoDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
