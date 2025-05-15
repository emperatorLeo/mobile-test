package com.example.mobile_test.data.datasource

import arrow.core.Either
import com.example.mobile_test.domain.SeedDto
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getSeed(): Flow<Either<Error,SeedDto>>
}