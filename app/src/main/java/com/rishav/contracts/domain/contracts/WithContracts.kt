package com.rishav.contracts.domain.contracts

import com.rishav.contracts.domain.model.User
import kotlin.contracts.*

@OptIn(ExperimentalContracts::class)
object WithContracts {

    inline fun isValid(user: User?): Boolean {
        contract {
            returns(true) implies (user != null)
        }
        return user != null && user.name.isNotBlank()
    }

    inline fun requireUser(user: User?): User {
        contract {
            returns() implies (user != null)
        }
        return user ?: throw IllegalStateException()
    }

    inline fun findUser(user: User?): User? {
        contract {
            returnsNotNull() implies (user != null)
        }
        return user
    }
}
