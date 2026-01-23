package com.dhruv.core.domain.model

data class GithubUser(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String
)