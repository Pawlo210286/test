package com.test.test.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel(): Kodein.Builder.TypeBinder<T> {
    return bind<T>(T::class.java.simpleName)
}

inline fun <reified VM, T> T.viewModel(): Lazy<VM>
        where T : KodeinActivity,
              VM : ViewModel {
    return lazy {
        ViewModelProvider(this as KodeinActivity, direct.instance(tag = KodeinActivity.DI_TAG))
            .get(VM::class.java).let {
                return@let it
            }
    }
}