package com.khdv.ghu.api

sealed class NetworkState {
    object Loading : NetworkState()
    object Loaded : NetworkState()
    class Error(val message: String? = null) : NetworkState()
}