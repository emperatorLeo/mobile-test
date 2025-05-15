package com.example.mobile_test.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_test.data.remote.SeedService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val seedService: SeedService
): ViewModel() {

    fun getSeeds() {
        viewModelScope.launch {
           val response = seedService.getSeed()
            Log.d("Leo", "code: ${response.code()}, message: ${response.message()}, success: ${response.isSuccessful}")

            Log.d("Leo", "response: ${response.body()}")
        }
    }
}