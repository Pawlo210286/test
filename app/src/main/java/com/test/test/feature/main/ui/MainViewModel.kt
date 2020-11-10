package com.test.test.feature.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.test.feature.main.api.MainComponent
import com.test.test.feature.main.api.MainUseCase
import com.test.test.feature.main.api.model.MainState

class MainViewModel(
    private val useCase: MainUseCase
) : ViewModel(), MainComponent {

    private val stateSource = MutableLiveData<MainState>()
    val state: LiveData<MainState> = stateSource

    fun init() {
        useCase.attachComponent(this)
    }

    fun onSearchQueryChanged(query: String) {
        useCase.searchQueryChanged(query)
    }

    fun onSearchClick() {
        useCase.searchClick()
    }

    fun onRefresh() {
        useCase.refresh()
    }

    override fun onCleared() {
        useCase.clear()
        super.onCleared()
    }

    override fun onStateUpdated(state: MainState) {
        stateSource.postValue(state)
    }
}