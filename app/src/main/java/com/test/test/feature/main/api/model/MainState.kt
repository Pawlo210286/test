package com.test.test.feature.main.api.model

import com.test.test.common.State

data class MainState(
    val query: String,
    val isProgress: Boolean,
    val reps: List<Rep>
): State()