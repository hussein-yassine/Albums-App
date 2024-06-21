package com.cme.songscompose.utils

sealed class ResourceState {
    data object LOADING : ResourceState()
    data object SUCCESS : ResourceState()
    data object ERROR : ResourceState()
}

data class Resource<out T>(
    val state: ResourceState,
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null
)

sealed class ResourceFlowState {
    data object IDLE: ResourceFlowState()
    data object LOADING: ResourceFlowState()
    data object SUCCESS: ResourceFlowState()
    data object ERROR: ResourceFlowState()
}

data class ResourceFlow<out T>(
    val state: ResourceFlowState,
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null
)
