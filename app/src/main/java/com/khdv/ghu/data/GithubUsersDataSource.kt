package com.khdv.ghu.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.khdv.ghu.api.GithubService
import com.khdv.ghu.api.NetworkState
import com.khdv.ghu.model.User
import com.khdv.ghu.model.UsersSearchResponse

class GithubUsersDataSource(
    private val githubApi: GithubService,
    private val query: String
) : PageKeyedDataSource<Int, User>() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        loadUsers(1, params.requestedLoadSize) {
            when (it) {
                null -> callback.onResult(emptyList(), null, null)
                else -> {
                    val nextKey = if (it.items.size == params.requestedLoadSize) 2 else null
                    callback.onResult(it.items, 0, it.totalCount, null, nextKey)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        val page = params.key
        loadUsers(page, params.requestedLoadSize) {
            when (it) {
                null -> callback.onResult(emptyList(), null)
                else -> {
                    val nextKey = if (it.items.size == params.requestedLoadSize) page + 1 else null
                    callback.onResult(it.items, nextKey)
                }
            }
        }
    }

    private fun loadUsers(page: Int, limit: Int, callback: (UsersSearchResponse?) -> Unit) {
        _networkState.postValue(NetworkState.Loading)
        try {
            val response = githubApi.searchUsers(query, page, limit).execute()
            if (response.isSuccessful) {
                _networkState.postValue(NetworkState.Loaded)
                callback(response.body())
            } else {
                _networkState.postValue(NetworkState.Error("${response.code()} ${response.message()}"))
            }
        } catch (exception: Exception) {
            _networkState.postValue(NetworkState.Error(exception.localizedMessage))
        }
    }
}