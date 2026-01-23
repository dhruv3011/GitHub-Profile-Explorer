package com.dhruv.githubprofileexplorer.navigation

import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute

@Serializable
data class RepoDetailRoute(
    val owner: String,
    val repo: String
)