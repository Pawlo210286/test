package com.test.test.feature.main.domain

import com.test.test.common.BaseInteractor
import com.test.test.common.MessageProcessor
import com.test.test.feature.main.api.IMainRemote
import com.test.test.feature.main.api.MainComponent
import com.test.test.feature.main.api.MainUseCase
import com.test.test.feature.main.api.model.MainState
import com.test.test.feature.main.api.model.Rep
import com.test.test.source.remote.common.RemoteResult
import kotlinx.coroutines.*

class MainInteractor(
    private val remote: IMainRemote,
    private val messageProcessor: MessageProcessor
) : BaseInteractor<MainState, MainComponent>(), MainUseCase {

    private var loadJob: Job? = null

    override fun searchQueryChanged(query: String) {
        state = state.copy(query = query)
    }

    override fun searchClick() {
        if (state.query.isEmpty()) {
            return
        }

        clear()

        val q = state.query
        state = state.copy(isProgress = true, reps = emptyList())

        loadJob = CoroutineScope(Dispatchers.IO).launch {
            awaitAll(
                async { processSearchResult(remote.search(q, 1)) },
                async { processSearchResult(remote.search(q, 2)) }
            )

            state = state.copy(isProgress = false)
        }
    }

    private fun processSearchResult(result: RemoteResult<List<Rep>>) {
        when (result) {
            is RemoteResult.SuccessResult -> {
                state = state.copy(
                    reps = state.reps + result.data
                )
            }
            is RemoteResult.ErrorResult -> {
                messageProcessor.showToast(result.error.localizedMessage.orEmpty())
            }
        }
    }

    override fun clear() {
        loadJob?.cancel()
        loadJob = null
    }

    private fun loadData() {
        clear()
        state = state.copy(isProgress = true)

        loadJob?.cancel()
        loadJob = CoroutineScope(Dispatchers.IO).launch {
            remote.getRepList().let {
                when (it) {
                    is RemoteResult.SuccessResult -> {
                        state = state.copy(
                            isProgress = false,
                            reps = it.data
                        )
                    }
                    is RemoteResult.ErrorResult -> {
                        state = state.copy(
                            isProgress = false
                        )
                        messageProcessor.showToast(it.error.localizedMessage.orEmpty())
                    }
                }
            }
        }
        loadJob?.start()
    }

    override fun refresh() {
        if (state.query.isEmpty()) {
            loadData()
        } else {
            searchClick()
        }
    }

    override fun initState(): MainState {
        return MainState(
            query = "",
            isProgress = true,
            reps = emptyList()
        )
    }

    override fun onComponentInitialized() {
        loadData()
    }

    override fun attachComponent(component: MainComponent) {
        attachBaseComponent(component)
    }
}