package com.example.mobile_test.data.datasource

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.mobile_test.data.remote.SeedService
import com.example.mobile_test.domain.SeedDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataSourceImp (private val seedService: SeedService) : DataSource {

    override suspend fun getSeed(): Flow<Either<Error, SeedDto>> = flow {
        val response = seedService.getSeed()
        if (response.isSuccessful) {
            emit(SeedDto(response.body()?.seed ?: "", response.body()?.expiration ?: "").right())
        } else {
                emit(Error(response.message()).left())
        }
    }
}