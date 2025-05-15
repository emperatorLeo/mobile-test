package com.example.mobile_test.usecase

import com.example.mobile_test.data.repository.Repository
import javax.inject.Inject

class GetSeedUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getSeed()
}