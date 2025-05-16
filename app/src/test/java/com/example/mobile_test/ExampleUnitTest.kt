package com.example.mobile_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.mobile_test.domain.SeedDto
import com.example.mobile_test.presentation.states.UiState
import com.example.mobile_test.presentation.viewmodel.MainViewModel
import com.example.mobile_test.usecase.GetSeedUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    @RelaxedMockK
    private lateinit var getSeedUseCase: GetSeedUseCase

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(getSeedUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel calls getSeeds and getSeeds is success, uiState should be success `() =

        runTest {
            // GIVEN
            val mockedSeed = SeedDto("123456789", "2025-05-15T18:49:14.258Z")
            val mockedFlow = flow<Either<Error, SeedDto>> { emit(mockedSeed.right()) }
            coEvery { getSeedUseCase.invoke() } returns mockedFlow


            // WHEN
            viewModel.getSeeds()

            // THEN
            assertEquals(UiState.Success(mockedSeed), viewModel.uiState.value)
        }

    @Test
    fun `when viewmodel calls getSeeds and getSeeds is Error, uiState should be Error `() =

        runTest {
            // GIVEN
            val mockerError = Error("Error")
            val mockedFlow = flow<Either<Error, SeedDto>> { emit(mockerError.left()) }
            coEvery { getSeedUseCase.invoke() } returns mockedFlow


            // WHEN
            viewModel.getSeeds()

            // THEN
            assertEquals(UiState.Error("Error"), viewModel.uiState.value)
        }
}