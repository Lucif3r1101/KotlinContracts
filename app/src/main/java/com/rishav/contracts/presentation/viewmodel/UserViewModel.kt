package com.rishav.contracts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.rishav.contracts.data.repository.UserRepository
import com.rishav.contracts.domain.contracts.LambdaContracts
import com.rishav.contracts.domain.contracts.WithContracts
import com.rishav.contracts.domain.contracts.WithoutContracts

/**
 * This ViewModel exists purely to demonstrate Kotlin Contracts.
 *
 * There is no UI, no runtime output, and no observable state.
 * The "output" of these functions is how the compiler behaves.
 */
class UserViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    /**
     * WITHOUT CONTRACTS
     *
     * Even though isValid() checks for null internally,
     * the compiler does NOT trust the result.
     *
     * This function is expected to produce a compiler error
     * on `user.name`.
     */
    fun withoutContracts() {
        val user = repository.fetchUser()

        if (WithoutContracts.isValid(user)) {
            // Compiler error:
            // Only safe (?.) or non-null asserted (!!.) calls are allowed
            user.name
        }
    }

    /**
     * WITH CONTRACTS (returns(true) implies condition)
     *
     * The contract tells the compiler:
     * "If this function returns true, the user is non-null."
     *
     * Smart cast works here.
     */
    fun withContracts() {
        val user = repository.fetchUser()

        if (WithContracts.isValid(user)) {
            // Compiles successfully
            user.name
        }
    }

    /**
     * require-style contract (returns() implies condition)
     *
     * If the function returns normally,
     * the compiler knows the user is non-null.
     */
    fun requireExample() {
        val user = WithContracts.requireUser(repository.fetchUser())
        user.id
    }

    /**
     * returnsNotNull() implies condition
     *
     * The existence of a non-null return value
     * proves something about the input parameter.
     */
    fun returnsNotNullExample() {
        val user = WithContracts.findUser(repository.fetchUser())
        user?.name
    }

    /**
     * LAMBDA WITHOUT CONTRACT
     *
     * The compiler cannot prove that the lambda
     * runs exactly once, so it refuses to treat
     * the variable as initialized.
     */
    fun lambdaWithoutContract() {
        lateinit var value: String

        run {
            value = "Initialized"
        }

        // Compiler error:
        // Variable 'value' must be initialized
        value.length
    }

    /**
     * LAMBDA WITH callsInPlace(EXACTLY_ONCE)
     *
     * The contract guarantees that the lambda
     * is executed exactly once.
     *
     * This allows the compiler to treat the
     * variable as safely initialized.
     */
    fun lambdaWithContract() {
        lateinit var value: String

        LambdaContracts.runExactlyOnce {
            value = "Initialized"
        }

        value.length
    }
}
