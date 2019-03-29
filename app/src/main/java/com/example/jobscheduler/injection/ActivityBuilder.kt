package com.example.jobscheduler.injection

import com.example.jobscheduler.MainActivity
import com.example.jobscheduler.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity
}