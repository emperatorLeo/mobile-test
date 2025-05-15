package com.example.mobile_test.di

import com.example.mobile_test.presentation.viewmodel.MainViewModel
import com.example.mobile_test.usecase.GetSeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideMainViewModel(getSeedUseCase: GetSeedUseCase): MainViewModel {
        return MainViewModel(getSeedUseCase)
    }
}