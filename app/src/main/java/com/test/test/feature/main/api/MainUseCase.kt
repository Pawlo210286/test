package com.test.test.feature.main.api

interface MainUseCase {
    fun searchQueryChanged(query: String)
    fun searchClick()
    fun refresh()

    fun clear()

    fun attachComponent(component: MainComponent)
}