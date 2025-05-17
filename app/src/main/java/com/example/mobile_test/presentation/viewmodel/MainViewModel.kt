package com.example.mobile_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_test.presentation.states.UiState
import com.example.mobile_test.usecase.GetSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val getSeedUseCase: GetSeedUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
        val uiState get() = _uiState.asStateFlow()

        fun getSeeds() {
            _uiState.value = UiState.Loading
            viewModelScope.launch {
                getSeedUseCase().collect { response ->
                    response.fold({
                        _uiState.value = UiState.Error(it.message ?: "error")
                    }, {
                        _uiState.value = UiState.Success(it)
                    })
                }
            }
        }
    }
