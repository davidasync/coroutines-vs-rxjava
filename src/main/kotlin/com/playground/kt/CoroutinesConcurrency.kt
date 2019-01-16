package com.playground.kt

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.Random

private fun defaultParallelism() = (Runtime.getRuntime().availableProcessors() - 1).coerceAtLeast(1)

fun randInt(min: Int, max: Int): Int {
    return Random().nextInt(max - min + 1) + min
}

fun intenseCalculation(i: Int): Int {
    try {
        println("Calculating " + i + " on " + Thread.currentThread().name)
        Thread.sleep(10)
        return i
    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    }
}

suspend fun main(args: Array<String>) = coroutineScope {
    val values = (1..100000)

    values.map {
        async { intenseCalculation(it) }
    }.forEach {
        println("Subscriber received ${it.await()} on ${Thread.currentThread()}")
    }
}
