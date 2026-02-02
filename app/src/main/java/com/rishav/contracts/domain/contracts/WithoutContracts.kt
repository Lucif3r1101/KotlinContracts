package com.rishav.contracts.domain.contracts

import com.rishav.contracts.domain.model.User

object WithoutContracts {

    fun isValid(user: User?): Boolean {
        return user != null && user.name.isNotBlank()
    }

    fun requireUser(user: User?): User {
        if (user == null) throw IllegalStateException()
        return user
    }

    inline fun runOnce(block: () -> Unit) {
        block()
    }
}
