package com.khdv.ghu.model

import com.squareup.moshi.Json

data class UsersSearchResponse(
    @field:Json(name = "total_count") val totalCount: Int,
    @field:Json(name = "items") val items: List<User>
)