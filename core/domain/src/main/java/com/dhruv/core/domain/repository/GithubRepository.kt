package com.dhruv.core.domain.repository

import com.dhruv.core.domain.model.GithubRepo
import com.dhruv.core.domain.model.GithubUser

interface GithubRepository {
     suspend fun getUserProfile(userName: String): GithubUser
     suspend fun getUserRepos(userName: String): List<GithubRepo>
     suspend fun getRepoDetails(owner: String, repo: String): GithubRepo
}