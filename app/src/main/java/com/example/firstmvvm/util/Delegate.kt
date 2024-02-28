package com.example.firstmvvm.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


@OptIn(DelicateCoroutinesApi::class)
fun <T> lazyDeferred(block : suspend CoroutineScope.() -> T): Lazy<Deferred<T>> =
    lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }