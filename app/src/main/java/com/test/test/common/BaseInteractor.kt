package com.test.test.common

import java.lang.ref.WeakReference

abstract class BaseInteractor<S : State, T : IComponent<S>> {

    private var component: WeakReference<T?> = initComponent()
    protected var state: S = initState()
        set(value) {
            synchronized(component) {
                field = value
            }
            component.get()?.onStateUpdated(value)
        }

    private var isInitialize = false

    private fun initComponent(): WeakReference<T?> {
        return WeakReference(null)
    }

    abstract fun initState(): S
    abstract fun onComponentInitialized()

    fun attachBaseComponent(component: T) {
        this.component = WeakReference(component)

        if (isInitialize.not()) {
            isInitialize = true
            onComponentInitialized()
        }
    }
}