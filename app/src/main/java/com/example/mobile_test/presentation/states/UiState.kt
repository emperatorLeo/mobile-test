package com.example.mobile_test.presentation.states

import com.example.mobile_test.domain.SeedDto

sealed class UiState {
    data class Success(val seed: SeedDto) : UiState()
    data class Error(val message: String) : UiState()
    data object Loading : UiState()
    data object Idle : UiState()
}