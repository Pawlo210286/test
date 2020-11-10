package com.test.test

import android.app.Application
import com.test.test.di.AppModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(AppModule.get(this@App))
    }

    override val kodeinTrigger = KodeinTrigger()

    override fun onCreate() {
        super.onCreate()

        kodeinTrigger.trigger()
    }
}