package com.deepak.myapplication.model

/**
 *
"use": "maiden",
"family": "Bono",
"given": [
"Kathleen"
],
"prefix": [
"Dr."
]
}
 */
@kotlinx.serialization.Serializable
data class Name(val use:String?, val family:String?, val given: List<String>?, val prefix: List<String>?)