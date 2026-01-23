package com.dhruv.core.data.mapper

import com.dhruv.core.domain.model.GithubRepo
import com.dhruv.core.domain.model.GithubUser
import com.dhruv.core.network.model.GithubRepoDto
import com.dhruv.core.network.model.GithubUserDto

fun GithubUserDto.toDomain(): GithubUser {
    return GithubUser(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl,
        htmlUrl = this.htmlUrl,
        name = this.name ?: this.login
    )
}

fun GithubRepoDto.toDomain(): GithubRepo {
    return GithubRepo(
        id = this.id,
        name = this.name,
        ownerName = owner.login,
        description = (this.description ?: "No description available") as String?,
        language = this.language ?: "Unknown",
        stargazersCount = this.stargazersCount,
        forksCount = this.forksCount
    )
}