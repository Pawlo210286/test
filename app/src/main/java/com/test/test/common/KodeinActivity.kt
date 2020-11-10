package com.test.test.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.test.feature.main.domain.MainInteractor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

abstract class KodeinActivity: AppCompatActivity(), KodeinAware {

    open val kodeinModule: Kodein.Module? = null

    private val parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(parentKodein)

        bind<ViewModelProvider.Factory>(tag = DI_TAG) with singleton { ViewModelFactory(direct) }

        kodeinModule?.let {
            import(it)
        }
    }

    override val kodeinTrigger = KodeinTrigger()

    override fun onCreate(savedInstanceState: Bundle?) {
        kodeinTrigger.trigger()
        super.onCreate(savedInstanceState)
    }

    companion object{
        const val DI_TAG = "activity"
    }
}