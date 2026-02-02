package com.rishav.contracts.domain.contracts

import kotlin.contracts.*

@OptIn(ExperimentalContracts::class)
object LambdaContracts {


    inline fun runExactlyOnce(block: () -> Unit) {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }
        block()
    }

    inline fun runAtLeastOnce(block: () -> Unit) {
        contract {
            callsInPlace(block, InvocationKind.AT_LEAST_ONCE)
        }
        do {
            block()
        } while (false)
    }

    inline fun runAtMostOnce(block: () -> Unit) {
        contract {
            callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        }
        if (System.currentTimeMillis() > 0) block()
    }

    inline fun runUnknown(block: () -> Unit) {
        contract {
            callsInPlace(block, InvocationKind.UNKNOWN)
        }
        block()
    }
}
