package com.test.test.feature.main.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.test.test.R
import com.test.test.common.KodeinActivity
import com.test.test.common.MessageProcessor
import com.test.test.common.bindViewModel
import com.test.test.common.viewModel
import com.test.test.feature.main.api.model.MainState
import com.test.test.feature.main.ui.list.RepListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MainActivity : KodeinActivity() {

    override val kodeinModule = Kodein.Module("Main") {
        bindViewModel<MainViewModel>() with provider {
            MainViewModel(
                useCase = instance()
            )
        }
    }

    private val viewModel: MainViewModel by viewModel()
    private val messageProcessor: MessageProcessor by instance<MessageProcessor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.init()

        viewModel.state.observe(this, Observer {
            updateState(it)
        })

        refresh.setOnRefreshListener {
            viewModel.onRefresh()
            refresh.isRefreshing = false
        }

        btnSearch.setOnClickListener {
            viewModel.onSearchClick()
        }

        etSearch.addTextChangedListener {
            viewModel.onSearchQueryChanged(it.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        messageProcessor.attach(this)
    }

    private fun updateState(state: MainState) {
        progressBar.isVisible = state.isProgress

        getAdapter().let {
            it as RepListAdapter
            it.submitList(state.reps)
        }

        emptyState.isVisible = state.reps.isEmpty() && state.isProgress.not()
    }

    private fun getAdapter() = repList.adapter ?: RepListAdapter().also {
        repList.adapter = it
        repList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}