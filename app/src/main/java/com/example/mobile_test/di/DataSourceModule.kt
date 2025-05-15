package com.example.mobile_test.di

import com.example.mobile_test.BuildConfig.BASE_URL
import com.example.mobile_test.data.datasource.DataSource
import com.example.mobile_test.data.datasource.DataSourceImp
import com.example.mobile_test.data.interceptor.FormulaInterceptor
import com.example.mobile_test.data.remote.SeedService
import com.example.mobile_test.data.repository.Repository
import com.example.mobile_test.data.repository.RepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRetrofitObject(): Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client =
            OkHttpClient.Builder().addInterceptor(logger).addInterceptor(FormulaInterceptor())
                .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideSeedService(retrofit: Retrofit): SeedService {
        return retrofit.create(SeedService::class.java)
    }

    @Provides
    fun provideDataSource(seedService: SeedService): DataSource {
        return DataSourceImp(seedService)
    }

    @Provides
    fun provideRepository(dataSource: DataSource): Repository {
        return RepositoryImp(dataSource)
    }
}