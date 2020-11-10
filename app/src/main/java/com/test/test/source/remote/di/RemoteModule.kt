package com.test.test.source.remote.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.test.feature.main.api.IMainRemote
import com.test.test.source.remote.repository.reps.RepsApi
import com.test.test.source.remote.repository.reps.RepsRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteModule {
    fun get() = Kodein.Module("Remote") {

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(instance())
                .build()
        }

        bind<OkHttpClient>() with singleton {
            val builder = OkHttpClient.Builder()

            builder.cache(instance())

            builder.connectTimeout(100, TimeUnit.SECONDS)

            builder.retryOnConnectionFailure(true)

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)

            builder.build()
        }

        bind() from provider {
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            Cache(instance<Context>().cacheDir, cacheSize.toLong())
        }

        bind<RepsApi>() with singleton { instance<Retrofit>().create(RepsApi::class.java) }

        bind<IMainRemote>() with provider {
            RepsRepository(
                api = instance()
            )
        }
    }

    private const val BASE_URL = "https://api.github.com/"
}