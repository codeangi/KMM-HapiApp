package com.deepak.myapplication.model

/**
 *
"use": "maiden",
"family": "Bono",
"given": [
"Kathleen"
]
}
 */
@kotlinx.serialization.Serializable
data class Name(val use:String?, val family:String?, val given: List<String>?)