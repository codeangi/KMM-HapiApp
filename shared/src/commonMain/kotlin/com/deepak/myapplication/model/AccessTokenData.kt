package com.deepak.myapplication.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class AccessTokenData(
    val scope: String,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Long
)