package com.khdv.ghu.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.khdv.ghu.api.GithubService
import com.khdv.ghu.model.User

class GithubUsersDataSourceFactory(
    private val githubApi: GithubService,
    private val query: String
) : DataSource.Factory<Int, User>() {

    private val _source = MutableLiveData<GithubUsersDataSource>()
    val source: LiveData<GithubUsersDataSource>
        get() = _source

    override fun create() = GithubUsersDataSource(githubApi, query).also(_source::postValue)
}