package com.example.sampleapp.characterlist.di

import com.example.sampleapp.characterlist.ui.ThroneViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjector {
    fun inject(throneViewModel: ThroneViewModel)



    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder

    }

}