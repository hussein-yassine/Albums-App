package com.cme.songscompose.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> MutableStateFlow<ResourceFlow<T>>.setSuccess(data: T, message: String? = null) =
    ResourceFlow(
        ResourceFlowState.SUCCESS,
        data,
        message
    ).copy()


fun <T> MutableStateFlow<ResourceFlow<T>>.setLoading() = ResourceFlow(
    ResourceFlowState.LOADING,
    value?.data
).copy()


fun <T> MutableStateFlow<ResourceFlow<T>>.setError(
    message: String? = null,
    exception: Exception? = null
) = ResourceFlow(
    ResourceFlowState.ERROR,
    value?.data,
    message,
    exception
).copy()
