package com.rishav.contracts.data.repository

import com.rishav.contracts.domain.model.User

class UserRepository {

    fun fetchUser(): User? {
        return User("1", "Rishav")
    }
}
