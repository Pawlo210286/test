package com.test.test.di

import android.app.Application
import com.test.test.common.MessageProcessor
import com.test.test.feature.main.api.MainUseCase
import com.test.test.feature.main.domain.MainInteractor
import com.test.test.source.remote.di.RemoteModule
import org.kodein.di.Kodein
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object AppModule {
    fun get(application: Application) = Kodein.Module("App") {
        import(androidCoreModule(application))
        import(RemoteModule.get())

        bind() from singleton {
            MessageProcessor()
        }

        bind<MainUseCase>() with provider {
            MainInteractor(
                remote = instance(),
                messageProcessor = instance()
            )
        }
    }
}