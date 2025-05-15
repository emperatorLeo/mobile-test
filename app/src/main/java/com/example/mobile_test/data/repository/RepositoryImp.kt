package com.example.mobile_test.data.repository

import arrow.core.Either
import com.example.mobile_test.data.datasource.DataSource
import com.example.mobile_test.domain.SeedDto
import kotlinx.coroutines.flow.Flow

class RepositoryImp(private val dataSource: DataSource) : Repository {
    override suspend fun getSeed(): Flow<Either<Error, SeedDto>> = dataSource.getSeed()
}