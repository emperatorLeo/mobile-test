package com.example.mobile_test.data.repository

import arrow.core.Either
import com.example.mobile_test.domain.SeedDto
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getSeed(): Flow<Either<Error, SeedDto>>
}