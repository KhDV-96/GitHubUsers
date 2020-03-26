package com.khdv.ghu.data

import androidx.lifecycle.switchMap
import androidx.paging.Config
import androidx.paging.toLiveData
import com.khdv.ghu.api.GithubService
import com.khdv.ghu.model.User

class GithubUsersRepository(private val githubApi: GithubService) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
        private val PAGED_LIST_CONFIG = Config(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSizeHint = NETWORK_PAGE_SIZE
        )
    }

    fun searchUsers(query: String): Listing<User> {
        val factory = GithubUsersDataSourceFactory(githubApi, query)
        val livePagedList = factory.toLiveData(PAGED_LIST_CONFIG)
        return Listing(livePagedList, factory.source.switchMap { it.networkState })
    }
}