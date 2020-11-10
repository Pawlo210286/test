package com.test.test.common

interface IComponent<T : State> {
    fun onStateUpdated(state: T)
}