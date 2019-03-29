package com.example.jobscheduler.injection

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.example.jobscheduler.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @Module
    companion object {
        @JvmStatic
        @Provides
        internal fun provideMainViewModel(): MainViewModel {
            return MainViewModel()
        }

    }
}