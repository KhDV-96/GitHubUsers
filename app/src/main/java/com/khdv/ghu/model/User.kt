package com.khdv.ghu.model

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "login") val login: String,
    @field:Json(name = "avatar_url") val avatarUrl: String,
    @field:Json(name = "html_url") val pageUrl: String,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "company") val company: String?
)