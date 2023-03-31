package com.deepak.myapplication.model

import kotlinx.serialization.SerialName

/**
 * {
"scope": "App",
"access_token": "eyJhbGciOiJSUzI1NiJ9.eyJ0ZW5hbnRfaWQiOiIwYmZjMzIxMy1kZWM3LTRhOTQtYjhiMi1iMDU5NGJhYmI5NzIiLCJpc3MiOiJjb20ueW1sIiwic3ViIjoiYTdiNzhmZTItMGYzNC00MWUxLWFjYzctOGMwZDQyMzU4ODkyIiwiZXhwIjoxNjgwMDkxNDc3LCJpYXQiOjE2ODAwODc4Nzd9.MI-88vd7fyy2sGw44NYwDmImzaWNJaj6ec3j_8cKfqKRnXOc3gZVNVfXcEXZUm-BJYme_ofhT_ZjagfpE5YT-0FgFTdDBw2JTHYrqLinqGo2uU8eXIx1GvlbK64f8q1bJI-M3helTjJSIaUzQCwSpBMqGlJ2M1ybo4hxtgTNCAocdTSRGOhe3CFG2xhi4erRFymnAkTPIzRwiC3WJUcG2kRwc5MafLGkp0N7pO2hYBa5nC4zRUQ_Mr1aKHTWMQaMAvRuvx-ZDxCeweOTshbGesB62CfQcswHHxk9Q87h2B5_wyrm1Y1TD1JHnzhDFMnvsyQbMUZXG7aegVUogkxxfw",
"token_type": "Bearer",
"expires_in": 3600
}
 */

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