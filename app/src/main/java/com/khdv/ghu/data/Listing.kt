package com.khdv.ghu.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.khdv.ghu.api.NetworkState

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>
)