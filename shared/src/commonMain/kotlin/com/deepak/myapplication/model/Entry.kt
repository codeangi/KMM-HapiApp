package com.deepak.myapplication.model

@kotlinx.serialization.Serializable
data class Entry(val fullUrl: String, val resource: Resource)

@kotlinx.serialization.Serializable
data class LocationEntry(val fullUrl: String, val resource: LocationResource)