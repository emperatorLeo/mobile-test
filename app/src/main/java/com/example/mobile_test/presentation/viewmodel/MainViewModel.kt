package com.example.mobile_test.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_test.usecase.GetSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSeedUseCase: GetSeedUseCase
) : ViewModel() {

    fun getSeeds() {
        viewModelScope.launch {
            getSeedUseCase().collect { response ->
                response.fold({
                    Log.d("Leo", "error: $it")
                }, {
                    Log.d("Leo", "success: $it")
                })
            }
        }
    }
}